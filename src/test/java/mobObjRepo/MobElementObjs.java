package mobObjRepo;

import baseclassTest.BaseclassMob;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MobElementObjs extends BaseclassMob {

    public MobElementObjs(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        PageFactory.initElements(driver, this);
    }

    @AndroidBy(xpath = "//android.widget.EditText[@text='Username']")
    @FindBy(how = How.XPATH, using = "//*[@AutomationId='username']")
    private WebElement eleUsername;

    public MobElementObjs enterUsername(String Username) {
        Platform platform = ((RemoteWebDriver) driver).getCapabilities().getPlatformName();
        hardWait(3000);
        switch (platform) {
            case ANDROID:
                WebElement ele = driver.findElement(By.xpath("//android.widget.EditText[@text='Username']"));
                enterText(ele, Username);
                break;
            case WINDOWS:
                enterText(eleUsername, Username);
                break;
            default:
                throw new IllegalArgumentException("Unsupported platform: " + platform);
        }
        return this;
    }

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Password']")
    @FindBy(how = How.XPATH, using = "//*[@AutomationId='inputPassword']")
    private WebElement elePassword;

    public MobElementObjs enterPassword(String Password) {
        Platform platform = ((RemoteWebDriver) driver).getCapabilities().getPlatformName();
        hardWait(3000);
        switch (platform) {
            case ANDROID:
                WebElement ele = driver.findElement(By.xpath("//android.widget.EditText[@text='Password']"));
                enterText(ele, Password);
                break;
            case WINDOWS:
                enterText(elePassword, Password);
                break;
            default:
                throw new IllegalArgumentException("Unsupported platform: " + platform);
        }
        return this;
    }

    @AndroidFindBy(xpath = "//android.widget.Button[@text='SIGN IN']")
    @FindBy(how = How.NAME, using = "Sign in")
    private WebElement elesignIn;
    public MobElementObjs clicksignIn() {
        Platform platform = ((RemoteWebDriver) driver).getCapabilities().getPlatformName();
        hardWait(3000);
        switch (platform) {
            case ANDROID:
                WebElement ele = driver.findElement(By.xpath("//android.widget.Button[@text='SIGN IN']"));
                click(ele);
                break;
            case WINDOWS:
                click(elesignIn);
                break;
            default:
                throw new IllegalArgumentException("Unsupported platform: " + platform);
        }
        return this;
    }
}
