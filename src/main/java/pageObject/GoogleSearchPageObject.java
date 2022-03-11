package pageObject;

import framework.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class GoogleSearchPageObject extends BaseClass {

    public WebDriver driver;

    public GoogleSearchPageObject(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.NAME, using = "q")
    public WebElement txtbox_google_search;

    @FindBy(how = How.XPATH, using = "//span[normalize-space()='Yahoo!']")
    public WebElement lbl_suggestive_search_val;



    public GoogleSearchResultPageObject googleSearch(String searchQuery){
        //we can also get element using getter method as well like below
        txtbox_google_search.sendKeys(searchQuery);
        lbl_suggestive_search_val.click();
        return new GoogleSearchResultPageObject(driver);
    }

}
