package baseclassTest;

import dataProvider.DataInputProvider;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;
import webUtility.WebActions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class BaseclassWeb extends WebActions {

	public String browserName;
	public String testDescription;
	public String runCategory;
	public String runGroup, WebhookUrl;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		startResult();
	}

	@Parameters({ "fileName", "jsonFilePath", "jsonDirectory", "url",
			"browser", "osVersion", "browserVersion", "execution_type", "platform", "pipeline_execution","headless" , "defectLog"})
	@BeforeClass(alwaysRun = true)
	public void beforeClass(@Optional String fileName,@Optional String jsonFilePath,@Optional String jsonDirectory,@Optional String url,
							@Optional String browser,@Optional String osVersion,@Optional String browserVersion,@Optional String execution_type,
							@Optional String platform,@Optional String pipeline_execution,@Optional boolean headless, @Optional String defectLog){
	}

	@Parameters({ "fileName", "jsonFilePath", "jsonDirectory", "url",
			"browser", "osVersion", "browserVersion", "execution_type", "platform", "pipeline_execution","headless", "defectLog"})
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(@Optional String fileName,@Optional String jsonFilePath,@Optional String jsonDirectory,@Optional String url,
							 @Optional String browser,@Optional String osVersion,@Optional String browserVersion,@Optional String execution_type,
							 @Optional String platform, @Optional String pipeline_execution,@Optional boolean headless, @Optional String defectLog) throws MalformedURLException {


		test = startTestModule(testCaseName + " // [" + url + "] - [" + browser + " - " + platform + "]", testDescription);
		test.assignCategory(runCategory);
		test.assignAuthor(authors);
		startApp(fileName, jsonFilePath, jsonDirectory, url,
				browser, osVersion,  browserVersion, execution_type, platform, pipeline_execution,headless, defectLog);

	}


	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		Properties prop = new Properties();
		try{
			prop.load(Files.newInputStream(Paths.get("./src/main/resources/config.properties")));
			WebhookUrl = prop.getProperty("Webhook");
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
        endResult();
		closeBrowser();
		//sendSlackNotification(WebhookUrl, result.getMethod().getMethodName());
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