package framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import pageObject.FinancePageObject;
import pageObject.GoogleSearchPageObject;
import pageObject.GoogleSearchResultPageObject;


public class PageProvider {

    private final WebDriver driver;
    public PageProvider(WebDriver driver) {
        this.driver = driver;
    }

    public GoogleSearchPageObject getGoogleSearchPage(){
        return new GoogleSearchPageObject(driver);
    }

    public GoogleSearchResultPageObject getGoogleSearchResultPage(){
        return new GoogleSearchResultPageObject(driver);
    }

    public FinancePageObject getFinancePage(){
        return new FinancePageObject(driver);
    }


}
