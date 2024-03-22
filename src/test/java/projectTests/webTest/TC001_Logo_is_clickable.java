package projectTests.webTest;

import baseclassTest.BaseclassWeb;
import org.testng.annotations.*;
import webObjRepo.WebElementObjs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TC001_Logo_is_clickable extends BaseclassWeb {

	private String uName = "";
	private String pWord = "";
	private Properties prop;
	private String platform = "";
	private String osVersion = "";
	private String deviceName = "";
	private String desktop = "";
	private String windowsDevice = "";

	@Parameters({ "executionType", "browser", "platform", "url" })
	@BeforeTest(alwaysRun = true)
	public void setData() {
		testCaseName = "Homepage logo clickable";
		dataSheetName 	= "getDataExcel";
		testDescription = "Ensure the logo exists and the page refreshes once clicked.";
		authors = "Vinoth";
		browserName = "chrome";
		runGroup = "Automation";
	}

	@Test(dataProvider = "fetchData")
	public void WebElementObjs(@Optional String columnData, String ProjectName) {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
			uName = prop.getProperty("hrmUsername");
			pWord = prop.getProperty("hrmPassword");
			platform = prop.getProperty("platform");
			osVersion = prop.getProperty("OSVersion");
			deviceName = prop.getProperty("deviceName");
			desktop = prop.getProperty("windowPlatform");
			windowsDevice = prop.getProperty("windowsDeviceName");

			new WebElementObjs(driver, test)
					.enterUserName(uName)
					.enterpassWord(pWord)
					.clickLogin()
					.clickProjects()
					.clickcreateProjects()
					.enterprojectsName(ProjectName)
					.entercreatedBy(ProjectName)
					.clickprojecStatus()
					.clickaddProject()
					.entersearchProject(ProjectName)
					.launchMobile_Apps(platform,osVersion,deviceName)
					.enterMobUsername(uName)
					.enterMobPassword(pWord)
					.clickMobsignIn()
					.clickMobprojects()
					.enterMobSearchprojects(ProjectName)
					.enterMobVerifyprojects(ProjectName)
					.launchDesktop(desktop, windowsDevice)
					.enterDTUsername(uName)
					.enterDTPassword(pWord)
					.clickDTsignIn();

		} catch (FileNotFoundException e) {
			hardFail();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			// testTearDown();
		}
	}
}