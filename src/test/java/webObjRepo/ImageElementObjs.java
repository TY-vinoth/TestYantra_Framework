package webObjRepo;

import baseclassTest.BaseclassWeb;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;


public class ImageElementObjs extends BaseclassWeb {

    public ImageElementObjs(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        PageFactory.initElements(driver, this);
    }

    public ImageElementObjs clickssignin() {
        if(executionType.equalsIgnoreCase("local")) {
            driver = new ChromeDriver();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.get("https://flutter.github.io/samples/web/web_dashboard/");
        }else{
           // BS Integration
        }
        hardWait(5000);
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\Signin.png";
        sikuli_clickAction(imagePath);
        return this;
    }

    public ImageElementObjs clicksettings() {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\updated.png";
        sikuli_clickAction(imagePath);
        return this;
    }

    public ImageElementObjs enterrenameValue(String str) {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\RenameValue.png";
        sikuli_enterText(imagePath, str);
        return this;
    }

    public ImageElementObjs clickeok() {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\ok.png";
        sikuli_clickAction(imagePath);
        return this;
    }

    public ImageElementObjs compareImages() {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\imageCompare.png";
        sikuli_ImageComparison(imagePath);
        return this;
    }

    public ImageElementObjs compareImagesWithExisting() {
        String imagePath = "C:\\Users\\USER1\\Documents\\Vinoth_Docs\\TestYantra_Framework\\TestYantra_Framework\\sikuliImages\\imageCompare.png";
        sikuli_Existing_ImageComparison(imagePath);
        return this;
    }
}
