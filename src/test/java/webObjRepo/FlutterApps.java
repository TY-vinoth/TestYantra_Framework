package webObjRepo;

import baseclassTest.BaseclassWeb;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class FlutterApps extends BaseclassWeb {

    public FlutterApps(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        PageFactory.initElements(driver, this);
    }

    public FlutterApps clickssignin() {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\Signin.png";
        sikuliClickAction(imagePath);
        return this;
    }

    public FlutterApps clicksettings() {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\updated.png";
        sikuliClickAction(imagePath);
        return this;
    }

    public FlutterApps enterrenameValue(String str) {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\RenameValue.png";
        sikuliEnterText(imagePath, str);
        return this;
    }

    public FlutterApps clickeok() {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\ok.png";
        sikuliClickAction(imagePath);
        return this;
    }
}
