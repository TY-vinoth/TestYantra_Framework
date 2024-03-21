package webObjRepo;

import baseclassTest.BaseclassWeb;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class DesktopElementObjs extends BaseclassWeb {

    public DesktopElementObjs(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "//input[@placeholder='Search by Project Id']")
    private WebElement elelaunchDesktop;
    public DesktopElementObjs launchDesktop() {
        driver=launchApp("Windows","WindowsPC","","local","");
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)),this);
        return this;
    }
    @FindBy(how = How.XPATH, using = "//*[@AutomationId='username']")
    private WebElement eleDTUsername;
    public DesktopElementObjs enterDTUsername(String Username) {
        hardWait(2000);
        enterText(eleDTUsername, Username);
        return this;
    }

    @FindBy(how = How.XPATH, using = "//*[@AutomationId='inputPassword']")
    private WebElement eleDTPassword;
    public DesktopElementObjs enterDTPassword(String Password) {
        hardWait(2000);
        enterText(eleDTPassword, Password);
        return this;
    }

    @FindBy(how = How.NAME, using = "Sign in")
    private WebElement eleDTsignIn;
    public DesktopElementObjs clickDTsignIn() {
        hardWait(2000);
        click(eleDTsignIn);
        return this;
    }
}
