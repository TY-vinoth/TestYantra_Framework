package webObjRepo;

import baseclassTest.BaseclassWeb;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.time.Duration;

public class MobileElementObjs extends BaseclassWeb {

    public MobileElementObjs(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "//input[@placeholder='Search by Project Id']")
    private WebElement elelaunchApp;
    public MobileElementObjs launchMobile_Apps(String platform, String deviceName, String OSVersion) {
        driver=launchApp(platform,deviceName,OSVersion,"local","bs_app_path");
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)),this);
        return this;
    }

    @FindBy(how = How.XPATH, using ="//android.widget.EditText[@resource-id='com.tyss.rmgyantra:id/EmailAddress']")
    private WebElement eleUsername;

    public MobileElementObjs enterMobUsername(String Username) {
        enterText(eleUsername, Username);
        return this;
    }

    @FindBy(how = How.XPATH, using ="//android.widget.EditText[@resource-id='com.tyss.rmgyantra:id/Password']")
    private WebElement elePassword;
    public MobileElementObjs enterMobPassword(String Password) {
        enterText(elePassword, Password);
        return this;
    }

    @FindBy(how = How.XPATH, using ="//android.widget.Button[@resource-id='com.tyss.rmgyantra:id/signIn']")
    private WebElement elesignIn;
    public MobileElementObjs clickMobsignIn() {
        click(elesignIn);
        return this;
    }

    @FindBy(how = How.ID, using ="com.tyss.rmgyantra:id/navigation_bar_item_small_label_view")
    private WebElement eleMobprojects;
    public MobileElementObjs clickMobprojects() {
        click(eleMobprojects);
        return this;
    }

    @FindBy(how = How.ID, using ="com.tyss.rmgyantra:id/searchEditText")
    private WebElement eleMobSearchprojects;
    public MobileElementObjs enterMobSearchprojects(String ProjectName) {
        enterText(eleMobSearchprojects, ProjectName);
        return this;
    }

    @FindBy(how = How.ID, using ="com.tyss.rmgyantra:id/projectNameTextView")
    private WebElement eleMobVerifyprojects;
    public DesktopElementObjs enterMobVerifyprojects(String ProjectName) {
        enterText(eleMobVerifyprojects, ProjectName);
        String str = eleMobVerifyprojects.getText();
        Assert.assertEquals(str, ProjectName);
        return new DesktopElementObjs(driver, test);
    }
}
