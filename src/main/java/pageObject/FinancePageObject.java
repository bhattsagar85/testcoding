package pageObject;

import framework.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class FinancePageObject {

    public WebDriver driver;

    public FinancePageObject(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(how= How.XPATH, using = "//a[@title='Finance']")
    public List<WebElement> txt_page_title;

    @FindBy(how = How.XPATH, using = "//input[@aria-label='Search for stocks, ETFs & more']")
    public List<WebElement> input_search;

    @FindBy(how = How.XPATH, using = "//div[@role='heading']")
    public List<WebElement> lbl_instrumentName;

    @FindBy(how = How.XPATH, using = "//*[text()='Sign in']")
    public List<WebElement> btn_sign_in;

    @FindBy(how = How.ID, using = "market-rundown-heading")
    public WebElement lbl_compare_market;

    @FindBy(how = How.XPATH, using = "//*[@role='tab']")
    public List<WebElement> tab_all_available;

    @FindBy(how = How.XPATH, using = "//*[text()='Follow']")
    public WebElement btn_follow;

    public void verifyFinancePageTitle(String pageTitle){
        Assert.assertEquals(txt_page_title.get(1).getText(), pageTitle);
    }

    public void searchQuote(String instrumentName){
        BaseClass baseClass = new BaseClass();
        boolean b = false;
        input_search.get(1).sendKeys(instrumentName);
        baseClass.findWebElementUsingDynamicLocators("*", "@data-display-name", instrumentName).click();
        baseClass.waitForElementToBePresent(btn_follow);
        for(int i=0; i<lbl_instrumentName.size();i++){
            if(lbl_instrumentName.get(i).getText().equalsIgnoreCase(instrumentName)){
                b = true;
                break;
            }
        }
        Assert.assertTrue(b, "Instrument Name: " + instrumentName + " is not displayed.");
    }

    public void verifyVariousControlOnPage(){
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(btn_sign_in.get(1).isDisplayed(), "Sign In option is not displayed.");
        softAssert.assertTrue(input_search.get(1).isDisplayed(), "Search for Quote textbox is not displayed.");
        if(tab_all_available.size()>5){
            softAssert.assertEquals(tab_all_available.get(0).getText(), "US", "US tab is missing.");
            softAssert.assertEquals(tab_all_available.get(1).getText(), "Europe", "Europe tab is missing.");
            softAssert.assertEquals(tab_all_available.get(2).getText(), "Asia", "Asia tab is missing.");
            softAssert.assertEquals(tab_all_available.get(3).getText(), "Currencies", "Currencies tab is missing.");
            softAssert.assertEquals(tab_all_available.get(4).getText(), "Crypto", "Crypto tab is missing.");
        }

    }
}
