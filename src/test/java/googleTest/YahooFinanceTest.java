package googleTest;

import framework.BaseClass;
import framework.PageProvider;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObject.FinancePageObject;
import pageObject.GoogleSearchPageObject;
import pageObject.GoogleSearchResultPageObject;


public class YahooFinanceTest extends BaseClass {


    private GoogleSearchPageObject googleSearchPageObject ;
    private GoogleSearchResultPageObject googleSearchResultPageObject;
    private FinancePageObject financePageObject;

    @BeforeMethod
    public void before(){
        googleSearchPageObject = pages().getGoogleSearchPage();
        googleSearchResultPageObject = pages().getGoogleSearchResultPage();
        financePageObject = pages().getFinancePage();
    }

    public YahooFinanceTest(){

    }


    @Test(priority = 0)
    public void navigateToFinancePage(){
        googleSearchResultPageObject = googleSearchPageObject.googleSearch("Yahoo");
        googleSearchResultPageObject.verifyGoogleSearchResultPageIsDisplayed();
        googleSearchResultPageObject.navigateToFinanceTab();
        financePageObject.verifyFinancePageTitle("Finance");
    }

    @Test(dependsOnMethods = {"navigateToFinancePage"}, priority = 2)
    public void searchQuote(){
        financePageObject.searchQuote("Tata Consultancy Services Limited");
    }

    @Test(dependsOnMethods = {"navigateToFinancePage"}, priority = 1)
    public void verifyVariousControl(){
        financePageObject.verifyVariousControlOnPage();
    }


}
