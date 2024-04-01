package projectTests.webTest;

import baseclassTest.BaseclassWeb;
import org.sikuli.script.FindFailed;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webObjRepo.ImageElementObjs;
import webObjRepo.WebElementObjs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TC002_Flutter_Application extends BaseclassWeb {

	private String uName = "";
	private String pWord = "";
	public String execution_Type="";
	private Properties prop;
	private String platform = "";
	private String osVersion = "";
	private String deviceName = "";
	private String chrome = "";
	private String windowsDevice = "";

	@Parameters({ "fileName", "jsonFilePath", "jsonDirectory", "url",
			"browser", "osVersion", "browserVersion", "executionType", "platform", "pipeline_execution" })
	@BeforeTest(alwaysRun = true)
	public void setData() {
		testCaseName = "Verify user can able to create and add the project in HRM";
		dataSheetName 	= "getDataExcel";
		testDescription = "User should be able to create and add the project in HRM";
		authors = "Vinoth";
		browserName = "chrome";
		runGroup = "Automation";
	}

	@Test(dataProvider = "fetchData")
	public void FlutterApps(@Optional String columnData, String ProjectName) {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
			chrome = prop.getProperty("hrmUsername");
			new ImageElementObjs(driver, test)
					.clickssignin()
					.clicksettings()
					.enterrenameValue("Vinoth")
					.clickeok();

		} catch (FileNotFoundException e) {
			hardFail();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			//testTearDown();
		}
	}
}