package baseclassTest;

import dataProvider.DataInputProvider;
import mobUtility.MobileActions;
import org.testng.annotations.*;

import java.net.MalformedURLException;

public class BaseclassMob extends MobileActions {

    public String testNodes;
    public String category;
    public String testCaseName;
    public String dataSheetName;
    public String testDescription;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        startResult();
    }

    @Parameters({ "platform", "deviceName", "OSVersion", "runIn", "bs_app_path","appActivity","appPackage" })
    @BeforeClass(alwaysRun = true)
    public void beforeClass( @Optional String platform,@Optional String deviceName,@Optional String OSVersion,@Optional String runIn,@Optional String bs_app_path,@Optional String appPackage,@Optional String appActivity){
    }

    @Parameters({ "platform", "deviceName", "OSVersion", "runIn", "bs_app_path","appActivity","appPackage"})
    @BeforeTest(alwaysRun = true)
    public void beforeTest( @Optional String platform,@Optional String deviceName,@Optional String OSVersion,@Optional String runIn,@Optional String bs_app_path,@Optional String appPackage,@Optional String appActivity){
    }

    @Parameters({"platform", "deviceName", "OSVersion", "runIn", "bs_app_path","appActivity","appPackage"})
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(@Optional String platform,@Optional String deviceName,@Optional String OSVersion,@Optional String runIn,@Optional String bs_app_path,@Optional String appPackage,@Optional String appActivity){
        if(deviceName.equalsIgnoreCase("WindowsPC")){
            test = startTestModule(" [" + platform + " - " + deviceName + "]" + testCaseName, testDescription);
        }else {
            test = startTestModule(" [" + platform + " - " + deviceName + " - "+ OSVersion  + "]" + testCaseName, testDescription);
        }
        test = startTestCase(testNodes);
        test.assignCategory(category);
        test.assignAuthor(authors);
        launchApp(platform, deviceName, OSVersion, runIn, bs_app_path, appPackage, appActivity);
    }


    @AfterMethod(alwaysRun=true)
    public void afterMethod(){
        endResult();
        //closeAllBrowsers();
    }

    @AfterTest(alwaysRun=true)
	public void afterTest(){
		endResult();
	}

    @AfterSuite(alwaysRun = true)
    public void afterSuite(){
        endResult();
    }

    @DataProvider(name="fetchData")
    public Object[][] getData(){
        return DataInputProvider.getSheet(dataSheetName);
    }

}
