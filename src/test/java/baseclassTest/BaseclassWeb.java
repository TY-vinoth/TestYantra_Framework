package baseclassTest;

import dataProvider.DataInputProvider;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;
import webUtility.WebActions;

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
			"browser", "osVersion", "browserVersion", "execution_type", "platform", "pipeline_execution" })
	@BeforeClass(alwaysRun = true)
	public void beforeClass(@Optional String fileName,@Optional String jsonFilePath,@Optional String jsonDirectory,@Optional String url,
							@Optional String browser,@Optional String osVersion,@Optional String browserVersion,@Optional String execution_type,
							@Optional String platform,@Optional String pipeline_execution){
	}

	@Parameters({ "fileName", "jsonFilePath", "jsonDirectory", "url",
			"browser", "osVersion", "browserVersion", "execution_type", "platform", "pipeline_execution" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(@Optional String fileName,@Optional String jsonFilePath,@Optional String jsonDirectory,@Optional String url,
							 @Optional String browser,@Optional String osVersion,@Optional String browserVersion,@Optional String execution_type,
							 @Optional String platform, @Optional String pipeline_execution) {


		test = startTestModule(testCaseName + " // [" + url + "] - [" + browser + " - " + platform + "]", testDescription);
		test.assignCategory(runCategory);
		test.assignAuthor(authors);
		startApp(fileName, jsonFilePath, jsonDirectory, url,
				browser, osVersion,  browserVersion, execution_type, platform, pipeline_execution);

		try{
			setTestEnvironment(fileName, jsonFilePath, jsonDirectory, url,
					 browser, osVersion,  browserVersion, execution_type, platform, pipeline_execution);
		}catch (Exception e){

		}
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