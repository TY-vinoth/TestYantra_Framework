package webObjRepo;

import baseclassTest.BaseclassWeb;
import com.aventstack.extentreports.ExtentTest;
import dBUtility.JDBCconnection;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class WebElementObjs extends BaseclassWeb {

    public WebElementObjs(RemoteWebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        PageFactory.initElements(driver, this);

    }

    @FindBy(how = How.XPATH, using = "(.//a[contains(text(),'Login')])")
    private WebElement eleLogin;
    public WebElementObjs clickLogin() {
        //db.sendQuery("select * from project","project_name");
        //db.sendQueryGetColumnData("select * from project","project_name","FireFlink_79693");
        //db.sendQueryGetColumnData("select * from project","project_name","FireFlink_79693");
       click(eleLogin);
        return this;
    }

    @FindBy(how = How.XPATH, using = "(.//input[@id='phone'])")
    private WebElement elemobileNumber;
    public WebElementObjs entermobileNumber(String MobileNumber) {
        enterText(elemobileNumber, MobileNumber);
        return this;
    }

    @FindBy(how = How.XPATH, using = "//button[@class='btn btn-primary loginBtn fs-16']")
    private WebElement eleotpLogin;
    public WebElementObjs clickotpLogin() {
        click(eleotpLogin);
        return this;
    }

}
