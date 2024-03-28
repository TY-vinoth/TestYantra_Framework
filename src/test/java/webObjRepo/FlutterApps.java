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
        sikuli_clickAction(imagePath);
        return this;
    }

    public FlutterApps clicksettings() {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\updated.png";
        sikuli_clickAction(imagePath);
        return this;
    }

    public FlutterApps enterrenameValue(String str) {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\RenameValue.png";
        sikuli_enterText(imagePath, str);
        return this;
    }

    public FlutterApps clickeok() {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\ok.png";
        sikuli_clickAction(imagePath);
        return this;
    }

    public FlutterApps compareImages() {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\imageCompare.png";
        sikuli_ImageComparison(imagePath);
        return this;
    }

    public FlutterApps compareImagesWithExisting() {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\imageCompare.png";
        sikuli_Existing_ImageComparison(imagePath);
        return this;
    }
}
