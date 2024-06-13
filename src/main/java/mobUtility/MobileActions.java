package mobUtility;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;
import listenerUtils.ReporterManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Optional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

public class MobileActions extends ReporterManager {

    private Logger log = Logger.getLogger(this.getClass().getName());

    public static WebDriver driver;
    public DesiredCapabilities caps;
    public MutableCapabilities W3C;
    public Properties prop;
    public static String BSUserName, BSPassword, LTUserName, LTPassword, SLUserName, SLPassword, URL, platform, browser;

    public MobileActions() {
        prop = new Properties();
        try {
            prop.load(new FileInputStream("./src/main/resources/config.properties"));
            BSUserName = prop.getProperty("USERNAME");
            BSPassword = prop.getProperty("ACCESS_KEY");
            LTUserName = prop.getProperty("LUSERNAME");
            LTPassword = prop.getProperty("LACCESS_KEY");
            SLUserName = prop.getProperty("SLUSERNAME");
            SLPassword = prop.getProperty("SLPASSWORD");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchApp(@Optional String platform, @Optional String deviceName, @Optional String OSVersion, @Optional String runIn, @Optional String bs_app_path, @Optional String appPackage, @Optional String appActivity, String testcaseName) {

        caps = new DesiredCapabilities();

        switch (runIn.toLowerCase()){
            case "browserstack":
                URL = "https://" + BSUserName + ":" + BSPassword + "@hub-cloud.browserstack.com/wd/hub";
                break;
            case "saucelabs":
                URL = "https://" + SLUserName + ":" + SLPassword + "ondemand.eu-central-1.saucelabs.com:443/wd/hub";
                break;
            case "lamdatest":
                URL = "https://" + LTUserName + ":" + LTPassword + "mobile-hub.lambdatest.com/wd/hub ";
                break;
        }

        try {
            switch (runIn.toLowerCase()) {
                case "local":
                    URL = "http://127.0.0.1:4723/wd/hub";
                    bs_app_path = "C:\\Users\\USER1\\Downloads\\NINZA HRM.apk";
                    caps.setCapability("app", bs_app_path);
                    if (platform.equalsIgnoreCase("windows")) {
                        caps.setCapability("automationName", "windows");
                        caps.setCapability("platformName", "windows");
                        bs_app_path = "C:\\Users\\USER1\\Documents\\TY\\hrm\\Ninza-HRM-win32-x64\\Ninza-HRM.exe";
                        caps.setCapability("app", bs_app_path);
                    }
                    break;
                case "browserstack":
                    switch (platform.toLowerCase()) {
                        case "android":
                            caps = new DesiredCapabilities();
                            HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
                            caps.setCapability("platformName", platform);
                            caps.setCapability("platformVersion", OSVersion);
                            caps.setCapability("deviceName", deviceName);
                            browserstackOptions.put("projectName", "Automation Test project");
                            browserstackOptions.put("sessionName", "NINZA HRM");
                            caps.setCapability("bstack:options", browserstackOptions);
                            caps.setCapability("unicodeKeyboard", true);
                            caps.setCapability("resetKeyboard", true);
                            caps.setCapability("autoDismissAlerts", true);
                            caps.setCapability("autoGrantPermissions", true);
                            caps.setCapability("noReset", true);
                            caps.setCapability("app", bs_app_path);
                            break;
                        case "ios":
                            caps.setCapability("platformName", platform);
                            caps.setCapability("deviceName", deviceName);
                            caps.setCapability("platformVersion", OSVersion);
                            caps.setCapability("automationName", "XCUITest");
                            caps.setCapability("connectHardwareKeyboard", true);
                            caps.setCapability("noReset", true);
                            caps.setCapability("name", testCaseName);
                            caps.setCapability("app", bs_app_path);
                            break;
                        // Add cases for other platforms if needed
                    }
                    break;
                case "saucelabs":
                    if (platform.equalsIgnoreCase("android")) {
                        MutableCapabilities sauceOptions = new MutableCapabilities();
                        W3C = new MutableCapabilities();
                        W3C.setCapability("platformName", platform);
                        W3C.setCapability("appium:app", bs_app_path);  // The filename of the mobile app
                        W3C.setCapability("appium:deviceName", deviceName);
                        W3C.setCapability("appium:platformVersion", OSVersion);
                        W3C.setCapability("appium:automationName", "UiAutomator2");
                        sauceOptions.setCapability("appiumVersion", "latest");
                        sauceOptions.setCapability("name", testcaseName);
                        W3C.setCapability("sauce:options", sauceOptions);
                        break;

                    } else if (platform.equalsIgnoreCase("ios")) {
                        W3C = new MutableCapabilities();
                        W3C.setCapability("appium:platformName", platform);
                        W3C.setCapability("appium:deviceName", deviceName);
                        W3C.setCapability("appium:platformVersion", OSVersion);
                        MutableCapabilities sauceOptions = new MutableCapabilities();
                        sauceOptions.setCapability("name", testCaseName);
                        sauceOptions.setCapability("build", "<your build id>");
                        W3C.setCapability("sauce:options", sauceOptions);
                        break;
                        // Add cases for other platforms if needed
                    }
                    break;
                case "lamdatest":
                    if (!platform.equalsIgnoreCase("android")) {
                        if (platform.equalsIgnoreCase("ios")) {
                            HashMap<String, Object> ltOptions = new HashMap<String, Object>();
                            ltOptions.put("w3c", true);
                            ltOptions.put("platformName", platform);
                            ltOptions.put("platformVersion", OSVersion);
                            ltOptions.put("deviceName", deviceName);
                            ltOptions.put("name", testCaseName);
                            ltOptions.put("automationName", "XCUITest");
                            ltOptions.put("connectHardwareKeyboard", true);
                            caps.setCapability("lt:options", ltOptions);
                            break;
                            // Add cases for other platforms if needed
                        }
                    } else {
                        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
                        ltOptions.put("platformName", platform);
                        ltOptions.put("platformVersion", OSVersion);
                        ltOptions.put("deviceName", deviceName);
                        ltOptions.put("isRealMobile", true);
                        ltOptions.put("unicodeKeyboard", true);
                        ltOptions.put("name", testCaseName);
                        ltOptions.put("resetKeyboard", true);
                        ltOptions.put("autoDismissAlerts", true);
                        ltOptions.put("autoGrantPermissions", true);
                        caps.setCapability("lt:options", ltOptions);
                        break;
                    }
                    break;
            }

            switch (platform.toLowerCase()) {
                case "android":
                    driver = new AndroidDriver(new URL(URL), caps);
                    break;
                case "windows":
                    driver = new WindowsDriver(new URL(URL), caps);
                    break;
                case "ios":
                    driver = new IOSDriver(new URL(URL), caps);
                    break;
                // Add cases for other platforms if needed
            }
            reportStep("The Application package:" + deviceName + " launched successfully", "PASS");
        } catch (MalformedURLException e) {
            reportStep("The Application package:" + deviceName + " could not be launched", "FAIL");
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
}
