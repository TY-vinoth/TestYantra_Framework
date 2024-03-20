package projectTests.webTest;

import baseclassTest.BaseclassWeb;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
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
	public void WebElementObjs(String ProjectName) {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
			uName = prop.getProperty("hrmUsername");
			pWord = prop.getProperty("hrmPassword");
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
					.apiExtractProjectName(ProjectName)
					.DBVerificationProjectName(ProjectName)
					.launchMobile_Apps()
					.enterMobUsername(uName)
					.enterMobPassword(pWord)
					.clickMobsignIn()
					.launchDesktop()
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