package projectTests.webTest;

import baseclassTest.BaseclassWeb;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webObjRepo.WebElementObjs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TC003_Json_Creator extends BaseclassWeb {

	private String uName = "";
	private String pWord = "";
	private Properties prop;

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
	public void WebElementObjs(@Optional String columnData, String ProjectName) {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
			uName = prop.getProperty("hrmUsername");
			pWord = prop.getProperty("hrmPassword");
			new WebElementObjs(test)
					.enterUserName(uName)
					.enterpassWord(pWord)
					.clickLogin();
		} catch (FileNotFoundException e) {
			hardFail();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}