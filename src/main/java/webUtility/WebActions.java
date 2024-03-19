package webUtility;

import dBUtility.JDBCconnection;
import io.github.bonigarcia.wdm.WebDriverManager;
import listenerUtils.ReporterManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.logging.Logger;

public class WebActions extends ReporterManager {

	public RemoteWebDriver driver;
	public String BSUserName, BSPassword, browser;
	public DesiredCapabilities dc;
	private Logger log = Logger.getLogger(this.getClass().getName());

	public WebActions() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("./src/main/resources/config.properties"));
			BSUserName = prop.getProperty("USERNAME");
			BSPassword = prop.getProperty("ACCESS_KEY");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void remoteExecution() {
		try {
			MutableCapabilities capabilities = new MutableCapabilities();
			HashMap<String, Object> bstackOptions = new HashMap<>();
			capabilities.setCapability("browserName", browser);
			capabilities.setCapability("name",testCaseName);
			bstackOptions.put("os", "Windows");
			bstackOptions.put("osVersion", "11");
			bstackOptions.put("browserVersion", "latest");
			bstackOptions.put("consoleLogs", "info");
			capabilities.setCapability("bstack:options", bstackOptions);

			driver = new RemoteWebDriver(new URL("https://" + BSUserName + ":" + BSPassword + "@hub-cloud.browserstack.com/wd/hub"), capabilities);
			reportStep("[" + browser + "] launched successfully in BrowserStack", "PASS");
		}catch(Exception e) {
			reportStep("[" + browser + "] hasn't launched successfully in BrowserStack", "FAIL");
		}
	}

	public void startApp(String executionType, String browser, String platform, String applicationUrl, String testCaseName) {

		switch (browser) {
			case "chrome":
				try {
					if (executionType.equalsIgnoreCase("local")) {
						driver = new ChromeDriver();
						ChromeOptions options = new ChromeOptions();
						options.addArguments("--remote-allow-origins=*");
					} else {
						remoteExecution();
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					log.warning("Launching URL --> " + applicationUrl);
					driver.get(applicationUrl);
					driver.manage().window().maximize();
					reportStep("[" + browser + "] launched successfully", "PASS");
				} catch (Exception e) {
					reportStep("[" + browser + "]: could not be launched", "FAIL");
					hardFail();
				}
				break;
			case "firefox":
				try {
					if (executionType.equalsIgnoreCase("local")) {
						WebDriverManager.firefoxdriver().setup();
						driver = new FirefoxDriver();
						FirefoxOptions options = new FirefoxOptions();
						options.addArguments("--remote-allow-origins=*");
					} else {
						remoteExecution();
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					log.warning("Launching URL --> " + applicationUrl);
					driver.get(applicationUrl);
					driver.manage().window().maximize();
					reportStep("[" + browser + "] launched successfully", "PASS");
				} catch (Exception e) {
					reportStep("[" + browser + "]: could not be launched", "FAIL");
					//hardFail("Firefox Session Not created !!");
				}
				break;
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
	public void switchToWindow(int index) {
		if (driver != null) {
			try {
				Set<String> allWindowHandles = driver.getWindowHandles();
				List<String> allHandles = new ArrayList<>();
				allHandles.addAll(allWindowHandles);
				driver.switchTo().window(allHandles.get(index));
			} catch (NoSuchWindowException e) {
				reportStep("FAIL", "The browser could not move to the given window by index " + index);
			} catch (WebDriverException e) {
				reportStep("FAIL", "WebDriverException : " + e.getMessage());
			}
		}
	}

	public void enterText(WebElement ele, String data) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.clear();
			ele.sendKeys(data);
			/*if (data.matches("^[\\w_*^)!]*$")){
				data = "****";
			}*/
			reportStep("The data: " + data + " entered successfully in field :" + "", "PASS");
		} catch (InvalidElementStateException e) {
		} catch (WebDriverException e) {
			reportStep("WebDriverException" + e.getMessage(), "FAIL");
		}
	}

	public void click(WebElement ele) {

		String text = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			text = ele.getText();
			ele.click();
			reportStep("The element : " + text + " is clicked ", "PASS");
		} catch (InvalidElementStateException e) {
			reportStep("The element: " + ele + " is not interactable", "FAIL");
		} catch (WebDriverException e) {
			reportStep("WebDriverException" + e.getMessage(), "FAIL");
		}
	}


	public WebElement locateElement(String locator, String locValue) {
		try {
			switch (locator) {

				case ("id"):
					return driver.findElement(By.id(locValue));
				case ("link"):
					return driver.findElement(By.linkText(locValue));
				case ("xpath"):
					return driver.findElement(By.xpath(locValue));
				case ("name"):
					return driver.findElement(By.name(locValue));
				case ("class"):
					return driver.findElement(By.className(locValue));
				case ("tag"):
					return driver.findElement(By.tagName(locValue));
			}
		} catch (NoSuchElementException e) {
			reportStep("The element with locator " + locator + " and with value " + locValue + " not found.", "FAIL");
			throw new RuntimeException();
		} catch (WebDriverException e) {
			reportStep("WebDriverException", "FAIL");
		}
		return null;
	}
	public void closeBrowser() {
		try {
			driver.close();
			reportStep("The browser is closed", "PASS", false);
		} catch (Exception e) {
			reportStep("The browser could not be closed: \n Error: " + e.getMessage(), "WARNING", false);
		}
	}

}
