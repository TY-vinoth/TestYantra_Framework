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

    @FindBy(how = How.XPATH, using = "//textarea[@class='gLFyf']/preceding-sibling::div")
    private WebElement elePassword;
    public WebElementObjs enterPassword(String Password) {
        //db.sendQuery("select * from project","project_name");
        //db.sendQueryGetColumnData("select * from project","project_name","FireFlink_79693");
        db.sendQueryGetColumnData("select * from project","project_name","FireFlink_79693");
        enterText(elePassword, Password);
        return this;
    }
}
