package projectTests.mobileTest.appTests;

import baseclassTest.BaseclassMob;
import mobObjRepo.appPages.HrmLoginPage;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TC001_Logo_Is_Clickable extends BaseclassMob {

    private String uName = "";
    private String pWord = "";
    private Properties prop;

    @Parameters({"platform", "deviceName", "OSVersion", "runIn", "bs_app_path"})
    @BeforeTest(alwaysRun = true)
    public void setData() {
        testCaseName = "Homepage logo clickable";
        dataSheetName 	= "getDataExcel";
        testDescription = "Ensure the logo exists and the page refreshes once clicked.";
        authors = "Vinoth";
        testNodes = "Nodes";
        category = "Regression";
    }

    @Test(dataProvider = "fetchData")
    public void hRMhomePage(@Optional String columnData, String ProjectName) {
        prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
            uName = prop.getProperty("hrmUsername");
            pWord = prop.getProperty("hrmPassword");
            new HrmLoginPage(driver, test)
                    .enterUsername(uName)
                    .enterPassword(pWord)
                    .clicksignIn();
        } catch (FileNotFoundException e) {
            hardFail();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // testTearDown();
        }
    }
}
