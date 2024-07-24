package combineTest;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class SeleniumPlaywrightFallback {

    private static WebDriver driver;
    private static Page playwrightPage;
    private static Browser playwrightBrowser;
    private static Playwright playwright;

    public static void main(String[] args) {
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-debugging-port=9222");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get("http://106.51.90.215:8084/");
            driver.manage().window().maximize();

            playwright = Playwright.create();
            playwrightBrowser = playwright.chromium().connectOverCDP("http://localhost:9222");
            playwrightPage = playwrightBrowser.contexts().get(0).pages().get(0);

            // Example usage
            enterFallback("xpath=//input[@id='username']", "rmgyantra");
            waitFor(100);
            enterFallback("xpath=//input[@id='inputPassword']", "rmgy@9999");
            waitFor(100);
            performActionWithFallback("xpath=//button[contains(text(),'Sign in')]");

        } finally {
            if (driver != null) {
                driver.quit();
            }
            if (playwrightBrowser != null) {
                try {
                    playwrightBrowser.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (playwright != null) {
                try {
                    playwright.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void performActionWithFallback(String selector) {
        try {

            WebElement element = findElementBySelector(selector);
            if (element.isDisplayed()) {
                element.click();
                System.out.println("Action performed using Selenium.");
            }
        } catch (WebDriverException e) {
            System.out.println("Element not found with Selenium, switching to Playwright.");
            try {
                Locator locator = playwrightPage.locator(convertSelector(selector));
                locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
                locator.click();
                System.out.println("Action performed using Playwright.");
            } catch (PlaywrightException ex) {
                System.err.println("Failed to perform action with Playwright: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public static void enterFallback(String selector, String str) {
        try {

            WebElement element = findElementBySelector(selector);
            if (element.isDisplayed()) {
                element.click();
                element.sendKeys(str);
                System.out.println("Action performed using Selenium.");
            }
        } catch (WebDriverException e) {
            System.out.println("Element not found with Selenium, switching to Playwright.");
            try {
                Locator locator = playwrightPage.locator(convertSelector(selector));
                locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
                // Debugging information
                System.out.println("Element found with Playwright: " + locator.innerText());
                locator.fill(str); // Fill the text
                System.out.println("Action performed using Playwright.");
            } catch (PlaywrightException ex) {
                System.err.println("Failed to perform action with Playwright: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private static WebElement findElementBySelector(String selector) {
        if (selector.startsWith("xpath=")) {
            return driver.findElement(By.id(selector.substring(6)));
        } else {
            return driver.findElement(By.id(selector));
        }
    }

    private static String convertSelector(String selector) {
        if (selector.startsWith("xpath=")) {
            return selector.substring(6);
        } else {
            return selector;
        }
    }

    private static void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}