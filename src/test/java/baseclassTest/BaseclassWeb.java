package baseclassTest;

import dataProvider.DataInputProvider;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;
import webUtility.WebActions;

import java.net.MalformedURLException;

public class BaseclassWeb extends WebActions {

	public String browserName;
	public String testDescription;
	public String runCategory;
	public String runGroup;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		startResult();
	}

	@Parameters({ "fileName", "jsonFilePath", "jsonDirectory", "url",
			"browser", "osVersion", "browserVersion", "execution_type", "platform", "pipeline_execution","headless" })
	@BeforeClass(alwaysRun = true)
	public void beforeClass(@Optional String fileName,@Optional String jsonFilePath,@Optional String jsonDirectory,@Optional String url,
							@Optional String browser,@Optional String osVersion,@Optional String browserVersion,@Optional String execution_type,
							@Optional String platform,@Optional String pipeline_execution,@Optional boolean headless){
	}

	@Parameters({ "fileName", "jsonFilePath", "jsonDirectory", "url",
			"browser", "osVersion", "browserVersion", "execution_type", "platform", "pipeline_execution","headless" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(@Optional String fileName,@Optional String jsonFilePath,@Optional String jsonDirectory,@Optional String url,
							 @Optional String browser,@Optional String osVersion,@Optional String browserVersion,@Optional String execution_type,
							 @Optional String platform, @Optional String pipeline_execution,@Optional boolean headless) throws MalformedURLException {


		test = startTestModule(testCaseName + " // [" + url + "] - [" + browser + " - " + platform + "]", testDescription);
		test.assignCategory(runCategory);
		test.assignAuthor(authors);
		startApp(fileName, jsonFilePath, jsonDirectory, url,
				browser, osVersion,  browserVersion, execution_type, platform, pipeline_execution,headless);

	}


	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		endResult();
		closeBrowser();
	}

	@AfterSuite()
	public void afterSuite(){
		endResult();
	}

	@DataProvider(name="fetchData")
	public String[][] getData(){
		return DataInputProvider.getSheet(dataSheetName);
	}
}