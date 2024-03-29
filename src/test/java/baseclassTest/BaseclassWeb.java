package baseclassTest;

import dataProvider.ExcelDataProvider;
import org.testng.ITestResult;
import org.testng.annotations.*;
import webUtility.WebActions;

import java.io.IOException;
import java.lang.reflect.Method;

public class BaseclassWeb extends WebActions {

	public String browserName;
	public String testDescription;
	public String runCategory;
	public String runGroup;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		startResult();
	}

	@Parameters({ "executionType", "browser", "platform", "url" })
	@BeforeClass(alwaysRun = true)
	public void beforeClass(@Optional String executionType, @Optional  String browser, @Optional  String platform, @Optional  String applicationUrl){
		startTestModule(testCaseName, testDescription);
	}

	@Parameters({ "executionType", "browser", "platform", "url" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(@Optional String executionType, @Optional  String browser, @Optional  String platform, @Optional  String applicationUrl) {
		test = startTestModule(testCaseName + " // [" + applicationUrl + "] - [" + browser + " - " + platform + "]", testDescription);
		test.assignCategory(runCategory);
		test.assignAuthor(authors);
		startApp(executionType, browser, platform, applicationUrl, testCaseName);
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

	/*@DataProvider(name = "getDataFromExcel")
	public Object[][] excelDataProvider(Method method) {
		ExcelDataProvider excelDataProvider = new ExcelDataProvider();
		Object[][] dataProvider = excelDataProvider.getExcelData();
		return dataProvider;
	}*/
}