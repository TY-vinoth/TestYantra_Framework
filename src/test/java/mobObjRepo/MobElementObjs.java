package mobObjRepo;

import baseclassTest.BaseclassMob;
import com.aventstack.extentreports.ExtentTest;
import dBUtility.JDBCconnection;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MobElementObjs extends BaseclassMob {

    public MobElementObjs(AppiumDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        PageFactory.initElements(driver, this);
    }

    @WindowsFindBy(accessibility="Something")
    @AndroidFindBy(xpath="(.//android.widget.LinearLayout[@resource-id='com.m.qr:id/expand_header_layout'])[2]")
    @iOSXCUITFindBy(accessibility="Child 1")
    private WebElement eleChild_one;
    public MobElementObjs clickChild_one() {
        click(eleChild_one);
        return this;
    }
}
