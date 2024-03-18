package mobUtility;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.windows.WindowsDriver;
import listenerUtils.ReporterManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

public class MobileActions extends ReporterManager {

    private Logger log = Logger.getLogger(this.getClass().getName());

    public WebDriver driver;
    public DesiredCapabilities caps;
    public Properties prop;
    public static String userName = "";
    public static String accessKey = "";
    public String URL;

    public MobileActions() {
        prop = new Properties();
        try {
            prop.load(new FileInputStream(".\\src\\main\\resources\\config.properties"));
            userName = prop.getProperty("USERNAME");
            accessKey = prop.getProperty("ACCESS_KEY");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startApp(String platform, String deviceName, String OSVersion, String runIn, String bs_app_path,String testCaseName,String appPackage, String appActivity) {

        URL = "https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub";

        caps = new DesiredCapabilities();

        try {
            if(runIn.equalsIgnoreCase("local")) {

                URL = "http://127.0.0.1:4723/wd/hub";
                //bs_app_path= "C:\\Users\\USER1\\Documents\\TY\\hrm\\Ninza-HRM-win32-x64\\Ninza-HRM.exe";
                bs_app_path= "C:\\Users\\USER1\\Downloads\\NINZA HRM.apk";
                if(platform.equalsIgnoreCase("Windows")){
                    caps.setCapability("automationName", "windows");
                    caps.setCapability("platformName", "windows");

                }else {
                    caps.setCapability("appPackage",appPackage);
                    caps.setCapability("appActivity",appActivity);
                }

            } else if(runIn.equalsIgnoreCase("remote")) {
                if (platform.equalsIgnoreCase("Android")){
                    caps.setCapability("platformName", platform);
                    caps.setCapability("platformVersion", OSVersion);
                    caps.setCapability("project", "Mobile Application");
                    caps.setCapability("unicodeKeyboard", true);
                    caps.setCapability("resetKeyboard", true);
                    caps.setCapability("autoDismissAlerts", true);
                    caps.setCapability("autoGrantPermissions", true);
                }
                else {
                    caps.setCapability("platformName", platform);
                    caps.setCapability("platformVersion", OSVersion);
                    caps.setCapability("automationName", "XCUITest");
                    caps.setCapability("connectHardwareKeyboard", true);
                }
            }

            caps.setCapability("noReset", false);
            caps.setCapability("deviceName", deviceName);
            caps.setCapability("name", testCaseName);
            caps.setCapability("app", bs_app_path);

            if(platform.equalsIgnoreCase("Android")) {
                driver = new AndroidDriver(new URL(URL), caps);
            } else if (platform.equalsIgnoreCase("windows")) {
                driver = new WindowsDriver(new URL(URL), caps);
            } else if (platform.equalsIgnoreCase("iOS")) {
                driver = new IOSDriver(new URL(URL), caps);
            }
            reportStep("The Appication package:" + deviceName + " launched successfully", "PASS");
        } catch (MalformedURLException e) {
            reportStep("The Appication package:" + deviceName + " could not be launched", "FAIL");
        }
    }

    public void click(WebElement ele) {
        String text = "";
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(ele)));
            text = ele.getText();
            ele.click();
            reportStep("The element : " + text + " is clicked ", "PASS");
        } catch (InvalidElementStateException e) {
            reportStep("The element: " + ele + " is not interactable", "FAIL");
            throw new InvalidElementStateException();
        } catch (WebDriverException e) {
            reportStep("WebDriverException" + e.getMessage(), "FAIL");
            throw new InvalidElementStateException();
        }
    }

    public long takeScreenShot() {

        long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
        try {
            File srcFiler = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFiler,
                    new File(System.getProperty("user.dir") + "/reports/images/" + number + ".png"));
        } catch (WebDriverException e) {
            log.warning("The snapshot has been taken.");
        } catch (IOException e) {
            log.warning("The snapshot has't been taken");
        }
        return number;
    }

    public void enterText(WebElement ele, String data) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            ele.clear();
            ele.sendKeys(data);
            if (data.matches("^[\\w_*^)!]*$")){
                data = "****";
            }
            reportStep("The data: " + data + " entered successfully in field :", "PASS");
        } catch (InvalidElementStateException e) {
            throw new InvalidElementStateException();
        } catch (WebDriverException e) {
            reportStep("WebDriverException" + e.getMessage(), "FAIL");
            throw new InvalidElementStateException();
        }
    }

    /*public void swipe(AppiumDriver driver, SwipeDirection direction) {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = size.height / 2;
        int endX = startX;
        int endY = startY;

        switch (direction) {
            case UP:
                endY = (int) (size.height * 0.2);
                break;
            case DOWN:
                endY = (int) (size.height * 0.8);
                break;
            case LEFT:
                endX = (int) (size.width * 0.2);
                break;
            case RIGHT:
                endX = (int) (size.width * 0.8);
                break;
        }

        TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);
        touchAction.press(PointOption.point(startX, startY))
                .moveTo(PointOption.point(endX, endY))
                .release()
                .perform();
    }

    public enum SwipeDirection {
        UP, DOWN, LEFT, RIGHT
    }*/
}
