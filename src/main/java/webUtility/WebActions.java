package webUtility;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dataProvider.bean.exceptions.ThrowableTypeAdapter;
import dataProvider.bean.remoteenv.RemoteEnvPojo;
import dataProvider.bean.testenv.TestEnv;
import dataProvider.bean.testenv.TestEnvPojo;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import listenerUtils.ReporterManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Optional;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class WebActions extends ReporterManager {

	public WebDriver driver;
	public String BSUserName, BSPassword, browser, URL, platform;
	public DesiredCapabilities caps;
	private final Logger log = Logger.getLogger(this.getClass().getName());


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

	private MutableCapabilities getCapabilities(String browser, String testCaseName) {
		MutableCapabilities capabilities = new MutableCapabilities();
		HashMap<String, Object> bstackOptions = new HashMap<>();
		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("name", testCaseName);
		bstackOptions.put("os", "Windows");
		bstackOptions.put("osVersion", "11");
		bstackOptions.put("browserVersion", "latest");
		bstackOptions.put("consoleLogs", "info");
		capabilities.setCapability("bstack:options", bstackOptions);
		return capabilities;
	}

	public WebDriver startApp(@Optional String fileName,@Optional String jsonFilePath,@Optional String jsonDirectory,@Optional String url,
							  @Optional String browser,@Optional String osVersion,@Optional String browserVersion,@Optional String execution_type,
							  @Optional String platform,@Optional String pipeline_execution) {

		URL = "https://" + BSUserName + ":" + BSPassword + "@hub-cloud.browserstack.com/wd/hub";


		execution_type = System.getProperty("execution_type","remote");
		browser = System.getProperty("browser_type","chrome");
		platform = System.getProperty("platform_type","web");

		switch (browser) {
			case "chrome":
				try {
					if (execution_type.equalsIgnoreCase("local")) {
						driver = new ChromeDriver();
						ChromeOptions options = new ChromeOptions();
						options.addArguments("--remote-allow-origins=*");
					} else if (execution_type.equalsIgnoreCase("remote")){
						try {
							MutableCapabilities capabilities = getCapabilities(browser, testCaseName);
							driver = new RemoteWebDriver(new URL(URL), capabilities);
							reportStep("[" + browser + "] launched successfully in BrowserStack", "PASS");
						} catch (Exception e) {
							reportStep("[" + browser + "] hasn't launched successfully in BrowserStack", "FAIL");
						}
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					log.warning("Launching URL --> " + url);
					driver.get(url);
					driver.manage().window().maximize();
					reportStep("[" + browser + "] launched successfully", "PASS");
				} catch (Exception e) {
					reportStep("[" + browser + "]: could not be launched", "FAIL");
					hardFail();
				}
				break;
			case "firefox":
				try {
					if (execution_type.equalsIgnoreCase("local")) {
						WebDriverManager.firefoxdriver().setup();
						driver = new FirefoxDriver();
						FirefoxOptions options = new FirefoxOptions();
						options.addArguments("--remote-allow-origins=*");
					} else {
						//remoteExecution();
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					log.warning("Launching URL --> " + url);
					driver.get(url);
					driver.manage().window().maximize();
					reportStep("[" + browser + "] launched successfully", "PASS");
				} catch (Exception e) {
					reportStep("[" + browser + "]: could not be launched", "FAIL");
					//hardFail("Firefox Session Not created !!");
				}
				break;
		}
        return driver;
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
			//borderElement(ele);
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
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			//borderElement(ele);
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

	public void extractProjectName(String ProjectName) {
		try {
			RestAssured.baseURI = "http://106.51.90.215:8084";
			Response response = given()
					.contentType("application/json")
					.when()
					.get("/projects")
					.then()
					.statusCode(200)
					.extract().response();
			String projectname = response.jsonPath().getString("projectName");
			System.out.println(response.asString());
			if (projectname.contains(ProjectName)) {
				System.out.println("Validating from API Project Name: " + ProjectName);
				reportStep("API Response for Project Name", "PASS");
			} else {
				System.out.println("Project Name was not present");
			}
		} catch (Exception e) {
			reportStep("Project Name was not present", "FAIL");
		}
	}

	public WebDriver launchApp(@Optional String platform, @Optional String deviceName, @Optional String OSVersion, @Optional String runIn, @Optional String bs_app_path) {

		URL = "https://" + BSUserName + ":" + BSPassword + "@hub-cloud.browserstack.com/wd/hub";

		caps = new DesiredCapabilities();

		try {
			if (runIn.equalsIgnoreCase("local")) {

				URL = "http://127.0.0.1:4723/wd/hub";
				bs_app_path = "C:\\Users\\USER1\\Downloads\\NINZA HRM.apk";
				if (platform.equalsIgnoreCase("Windows")) {
					caps.setCapability("automationName", "windows");
					caps.setCapability("platformName", "windows");
					bs_app_path = "C:\\Users\\USER1\\Documents\\TY\\hrm\\Ninza-HRM-win32-x64\\Ninza-HRM.exe";

				}/*else {
                    caps.setCapability("appPackage",appPackage);
                    caps.setCapability("appActivity",appActivity);
                }*/

			} else if (runIn.equalsIgnoreCase("remote")) {
				if (platform.equalsIgnoreCase("Android")) {
					caps.setCapability("platformName", platform);
					caps.setCapability("platformVersion", OSVersion);
					caps.setCapability("project", "Mobile Application");
					caps.setCapability("unicodeKeyboard", true);
					caps.setCapability("resetKeyboard", true);
					caps.setCapability("autoDismissAlerts", true);
					caps.setCapability("autoGrantPermissions", true);
				} else {
					caps.setCapability("platformName", platform);
					caps.setCapability("platformVersion", OSVersion);
					caps.setCapability("automationName", "XCUITest");
					caps.setCapability("connectHardwareKeyboard", true);
				}
			}

			caps.setCapability("noReset", true);
			caps.setCapability("deviceName", deviceName);
			caps.setCapability("name", testCaseName);
			caps.setCapability("app", bs_app_path);

			if (platform.equalsIgnoreCase("Android")) {
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
		return driver;
	}

	public boolean switchContext(String contextname) throws InterruptedException, MalformedURLException {
		AndroidDriver android = new AndroidDriver(new URL(URL), caps);
		try {
			Set<String> contexts = android.getContextHandles();
			android.execute(DriverCommand.SET_TIMEOUT, ImmutableMap.of("ms", 10000, "type", "script"));
			System.out.println(contexts);
			for (String contextName : contexts) {
				if (contextName.contains("NATIVE_APP"))
					android.context(contextName);
				System.out.println(contextName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			reportStep("The Context could not be switched", "FAIL");
		}
		return true;
	}

	public void borderElement(WebElement ele) {
		// draw a border around the found element

		int target_len = 40;
		String text = "";
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='4px solid red'", ele);
			text = ele.getText();

			if(text.length() > target_len) {

				reportStep("Testing element " + text.replaceAll(text, ele.getTagName()) + " has been highlighted.", "SKIP");

				hardWait(500);
				js.executeScript("arguments[0].style.border='0px solid blue'", ele);
			}
			else {

				reportStep("Testing element " + text + " has been highlighted.", "SKIP");

				hardWait(500);
				js.executeScript("arguments[0].style.border='0px solid blue'", ele);
			}
			hardWait(500);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			reportStep("Testing element " + text + " hasn't been highlighted.", "SKIP");
		}
		return;
	}

	/*public void setTestEnvironment(String testEnvPath, String fileName, String jsonFilePath, String jsonDirectory,String url,
								   String browser, String osVersion,  String browserVersion,String  execution_type, String platform) {
		try {
			Gson pGson = new GsonBuilder().registerTypeAdapter(Throwable.class, new ThrowableTypeAdapter()).setPrettyPrinting().create();
			JsonElement testEnvElement = null;
			try {
				if (fileName != null) {
					testEnvElement = JsonParser.parseReader(new FileReader(fileName));
				} else {
					testEnvElement = JsonParser.parseReader(new FileReader("tenv/test-env.json"));
				}
			} catch (FileNotFoundException e) {
				captureException(e);
			}
			JsonElement remoteEnvElement = JsonParser.parseReader(new FileReader("tenv/remote-env.json"));
			RemoteEnvPojo tRemoteEnv = pGson.fromJson(remoteEnvElement, RemoteEnvPojo.class);

			TestEnvPojo tLocalEnv = pGson.fromJson(testEnvElement, TestEnvPojo.class);
			envThreadLocal.set(new TestEnv());

			tEnv().setExecution_type(tRemoteEnv.getExecution_type());
			tEnv().setPipeline_execution(tRemoteEnv.getPipeline_execution());

			if (tLocalEnv.getWeb() != null) {
				tEnv().setWebSystemOS(tLocalEnv.getWeb().getSystemOs());
				tEnv().setWebSystemOSVersion(tLocalEnv.getWeb().getSystemOsVersion());
				tEnv().setWebBrowser(tLocalEnv.getWeb().getBrowser());
				tEnv().setWebHeadless(tLocalEnv.getWeb().getHeadless());
				tEnv().setWebBrowserVersion(tLocalEnv.getWeb().getBrowserVersion());
				tEnv().setWebUrl(tLocalEnv.getWeb().getWebUrl());
			}

			if (tRemoteEnv.getDb_config() != null) {
				tEnv().setDbHost(tRemoteEnv.getDb_config().get("dbHost").getAsString());
				tEnv().setDbUserName(tRemoteEnv.getDb_config().get("dbUserName").getAsString());
				tEnv().setDbPassword(tRemoteEnv.getDb_config().get("dbPassword").getAsString());
				tEnv().setDbName(tRemoteEnv.getDb_config().get("dbName").getAsString());
			}

			if (browser != null) {
				tEnv().setWebBrowser(browser);
			}
			if (browserVersion != null) {
				tEnv().setWebBrowserVersion(browserVersion);
			}
			if (url != null) {
				tEnv().setWebUrl(url);
			}
			if (jsonFilePath != null) {
				log.info("JSON File Path Set to " + jsonFilePath);
				tEnv().setJsonFilePath(jsonFilePath);
			}
			if (jsonDirectory != null) {
				tEnv().setJsonDirectory(jsonDirectory);
			} else {
				tEnv().setJsonDirectory("src/test/resources/TestData");
			}

		} catch (Exception e) {
			captureException(e);
		}
	}*/

}
