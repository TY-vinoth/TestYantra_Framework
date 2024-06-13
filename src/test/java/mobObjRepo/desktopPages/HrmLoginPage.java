package mobObjRepo.desktopPages;

import baseclassTest.BaseclassMob;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import mobUtility.MobileActions;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HrmLoginPage extends BaseclassMob {
    public HrmLoginPage(ExtentTest test) {
        this.test = test;
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "//*[@AutomationId='username']")
    private WebElement eleUsername;
    public HrmLoginPage enterUsername(String Username) {
        hardWait(2000);
        enterText(eleUsername, Username);
        return this;
    }

    @FindBy(how = How.XPATH, using = "//*[@AutomationId='inputPassword']")
    private WebElement elePassword;
    public HrmLoginPage enterPassword(String Password) {
        hardWait(2000);
        enterText(elePassword, Password);
        return this;
    }

    @FindBy(how = How.NAME, using = "Sign in")
    private WebElement elesignIn;
    public HrmLoginPage clicksignIn() {
        hardWait(2000);
        click(elesignIn);
        /*Platform platform = ((RemoteWebDriver) driver).getCapabilities().getPlatformName();
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
        }*/
        return this;
    }

    @FindBy(how = How.XPATH, using ="//a[@text='Projects']")
    private WebElement eleProjects;
    public HrmLoginPage clickProjects() {
        hardWait(2000);
        click(eleProjects);
        return this;
    }
}
