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
	private String android = "";
	private String osVersion = "";
	private String deviceName = "";
	private String window = "";
	private String windowsDevice = "";

	@Parameters({ "fileName", "jsonFilePath", "jsonDirectory", "url",
			"browser", "osVersion", "browserVersion", "executionType", "platform", "pipeline_execution","headless","defectLog" })
	@BeforeTest(alwaysRun = true)
	public void setData() {
		testCaseName = "Verify user can able to create and add the project in HRM";
		dataSheetName 	= "getDataExcel";
		testDescription = "User should be able to create and add the project in HRM";
		authors = "Vinoth";
		browserName = "chrome";
		runGroup = "Automation";
		testNodes = "Testing";
	}

	@Test(dataProvider = "fetchData")
	public void WebElementObjs(@Optional String columnData, String ProjectName) {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
			uName = prop.getProperty("hrmUsername");
			pWord = prop.getProperty("hrmPassword");
			android = prop.getProperty("platform");
			window = prop.getProperty("desktop");
			osVersion = prop.getProperty("OSVersion");
			deviceName = prop.getProperty("deviceName");
			windowsDevice = prop.getProperty("windowsDeviceName");

			new WebElementObjs(test)
					.enterUserName(uName)
					.enterpassWord(pWord)
					.clickLogin()
					.clickProjects()
					.clickcreateProjects()
					.enterprojectsName(ProjectName)
					.entercreatedBy(ProjectName)
					.clickprojecStatus()
					.clickaddProject()
					.entersearchProject(ProjectName);
					/*.launchMobile_Apps(android,deviceName,osVersion)
					.enterMobUsername(uName)
					.enterMobPassword(pWord)
					.clickMobsignIn()
					.clickMobprojects()
					.enterMobSearchprojects(ProjectName)
					.enterMobVerifyprojects(ProjectName)
					.launchDesktop(window, windowsDevice)
					.enterDTUsername(uName)
					.enterDTPassword(pWord)
					.clickDTsignIn()
					.clickssignin()
					.clicksettings()
					.enterrenameValue("Vinoth")
					.clickeok();*/

		} catch (FileNotFoundException e) {
			hardFail();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}