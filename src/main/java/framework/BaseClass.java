package framework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

/**
 * Project Name    : Selenium-Test-Coding
 * Developer       : Sagar Bhatt
 * Version         : 1.0.0
 * Date            : 9/3/2022
 * Time            : 11:59 PM
 * Description     :
 **/

public class BaseClass {

    private static final String fileSeparator = File.separator;
    protected static WebDriver driver;
    public static Properties config = new Properties();
    public static FileInputStream fis;
    public static Logger log = Logger.getLogger("devpinoyLogger");
    private ExtentReports extent;
    private static final String rootDirectory = System.getProperty("user.dir");

    @BeforeSuite
    public void setUp() throws IOException {
        fis = new FileInputStream(rootDirectory +fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator
                +  fileSeparator + "config.properties");
        config.load(fis);
        BasicConfigurator.configure();
        log.debug("Config file loaded !!!");
        String browserName = config.getProperty("browserName");
        String testUrl = config.getProperty("testUrl");
        int implicitTimeOut = Integer.parseInt(config.getProperty("implicitWait"));
        initializeDriver(browserName, testUrl, implicitTimeOut);
    }

    @BeforeTest
    public void extentReportSetup(){
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(rootDirectory + fileSeparator + "reports"+
                fileSeparator + "html-report" + fileSeparator + "execution-report.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        sparkReporter.config().setDocumentTitle("Test Execution Report - Automation Practise");
        sparkReporter.config().setReportName("Test Execution Report - Automation Practise");
        sparkReporter.config().setTheme(Theme.DARK);

        extent.setSystemInfo("Application Name", "Digital Fuel Admin Web");
        extent.setSystemInfo("Tester Name", "Sagar Bhatt");
        extent.setSystemInfo("Browser", "Chrome");
    }



    @AfterMethod
    public void generateReports(ITestResult result){
        ExtentTest test = extent.createTest(result.getName());
        switch (result.getStatus()){
            case ITestResult.FAILURE:
                test.log(Status.FAIL, MarkupHelper.createLabel(result.getName(), ExtentColor.RED));
                test.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable().getMessage(), ExtentColor.RED));
                try{
                    test.fail("Screenshot of the failed test is :"+
                            test.addScreenCaptureFromPath(takeScreenshot(driver, result.getName())));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case ITestResult.SKIP:
                test.log(Status.SKIP, MarkupHelper.createLabel(result.getName(), ExtentColor.GREY));
                break;
            case ITestResult.SUCCESS:
                test.log(Status.PASS, MarkupHelper.createLabel(result.getName(), ExtentColor.GREEN));
                break;
        }
    }

    @AfterSuite
    public void tearDown(){
        if(driver!=null){
            driver.quit();
        }
    }

    @AfterTest
    public void endReport(){
        extent.flush();
    }

    protected PageProvider pages() {
        return new PageProvider(driver);
    }


    public void initializeDriver(String browserName, String testUrl, int implicitTimeOut){
        driver = DriverManager.getDriver(browserName);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitTimeOut));
        driver.get(testUrl);
    }

    private String takeScreenshot(WebDriver driver, String screenshotName){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String destination = rootDirectory+fileSeparator+"screenshot"+fileSeparator+screenshotName
                +"-"+timeStamp+".png";
        File finalDestination = new File(destination);
        try{
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destination;
    }

    public void assertTrue(boolean b, String message){
        Assert.assertTrue(b, message);
    }

    public void waitForElementToBePresent(WebElement element){
        int wait = Integer.parseInt(config.getProperty("explicitWait"));
        new WebDriverWait(driver, Duration.ofSeconds(wait)).until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement findWebElementUsingDynamicLocators(String tagName, String attributeName, String attributeVal){
        String xpath = "//" + tagName + "["+attributeName+"='"+attributeVal + "']";
        return driver.findElement(By.xpath(xpath));
    }
}

