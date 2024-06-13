package baseclassTest;

import com.aventstack.extentreports.Status;
import dataProvider.DataInputProvider;
import org.testng.ITestResult;
import org.testng.annotations.*;
import webUtility.WebActions;

import java.net.MalformedURLException;
import java.util.List;

public class BaseclassWeb extends WebActions {

	public String browserName;
	public String testDescription;
	public String runCategory;
	public String runGroup, WebhookUrl;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		startResult();
	}

	@Parameters({"fileName", "jsonFilePath", "jsonDirectory", "url",
			"browser", "osVersion", "browserVersion", "execution_type", "platform", "pipeline_execution","headless" , "defectLog"})
	@BeforeClass(alwaysRun = true)
	public void beforeClass(@Optional String fileName,@Optional String jsonFilePath,@Optional String jsonDirectory,@Optional String url,
							@Optional String browser,@Optional String osVersion,@Optional String browserVersion,@Optional String execution_type,
							@Optional String platform,@Optional String pipeline_execution,@Optional boolean headless, @Optional String defectLog){
	}

	@Parameters({"fileName", "jsonFilePath", "jsonDirectory", "url",
			"browser", "osVersion", "browserVersion", "execution_type", "platform", "pipeline_execution","headless", "defectLog"})
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(@Optional String fileName,@Optional String jsonFilePath,@Optional String jsonDirectory,@Optional String url,
							 @Optional String browser,@Optional String osVersion,@Optional String browserVersion,@Optional String execution_type,
							 @Optional String platform, @Optional String pipeline_execution,@Optional boolean headless, @Optional String defectLog) throws MalformedURLException {
		test = startTestModule(testCaseName + " // [" + url + "] - [" + browser + " - " + execution_type + "]", testDescription);
		test = startTestCase(testNodes);
		test.assignCategory(runCategory);
		test.assignAuthor(authors);
		startApp(fileName, jsonFilePath, jsonDirectory, url,
				browser, osVersion,  browserVersion, execution_type, platform, pipeline_execution,headless, defectLog);

	}


	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE) {
			test.log(Status.FAIL, result.getMethod().getMethodName()+" is failed");
			test.log(Status.FAIL, result.getThrowable());
			try {
				String path = WebActions.getScreenshot(driver);
				test.addScreenCaptureFromPath(path);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(result.getStatus()==ITestResult.SUCCESS) {
			test.log(Status.PASS, result.getMethod().getMethodName()+" is passed");
		}
		else if(result.getStatus()==ITestResult.SKIP) {
			test.log(Status.SKIP, result.getMethod().getMethodName()+" is skipped");
			test.log(Status.SKIP, result.getThrowable());
		}
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