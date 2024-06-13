package mobObjRepo.appPages;

import baseclassTest.BaseclassMob;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HrmLoginPage extends BaseclassMob {

    public HrmLoginPage(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        PageFactory.initElements(driver, this);
    }


    @FindBy(how = How.XPATH, using ="//android.widget.EditText[@text='Username']")
    private WebElement eleUsername;
    public HrmLoginPage enterUsername(String Username) {
        enterText(eleUsername, Username);
        return this;
    }

    @FindBy(how = How.XPATH, using ="//android.widget.EditText[@text='Password']")
    private WebElement elePassword;
    public HrmLoginPage enterPassword(String Password) {
        enterText(elePassword, Password);
        return this;
    }

    @FindBy(how = How.XPATH, using ="//android.widget.Button[@text='SIGN IN']")
    private WebElement elesignIn;
    public HrmLoginPage clicksignIn() {
        click(elesignIn);
        return this;
    }
}
