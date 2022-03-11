package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class GoogleSearchResultPageObject {

    public  WebDriver driver;
    public GoogleSearchResultPageObject(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "//*[text()='All']")
    public WebElement tab_all;

    @FindBy(how = How.XPATH, using = "//*[text()='More']")
    public WebElement tab_more;

    @FindBy(how = How.XPATH, using = "//*[text()='Finance']")
    public WebElement tab_finance;



    public void verifyGoogleSearchResultPageIsDisplayed(){
        Assert.assertTrue(tab_all.isDisplayed(), "Search page is not displayed");
    }

    public FinancePageObject navigateToFinanceTab(){
        tab_more.click();
        tab_finance.click();
        return new FinancePageObject(driver);
    }


}
