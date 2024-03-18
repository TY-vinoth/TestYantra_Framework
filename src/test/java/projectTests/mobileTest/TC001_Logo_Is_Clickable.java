package projectTests.mobileTest;

import baseclassTest.BaseclassMob;
import mobObjRepo.MobElementObjs;
import org.testng.annotations.BeforeTest;
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
        testDescription = "Ensure the logo exists and the page refreshes once clicked.";
        authors = "Vinoth";
        testNodes = "Nodes";
        category = "Regression";
    }

    @Test()
    public void hRMhomePage() {
        prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
            uName = prop.getProperty("hrmUsername");
            pWord = prop.getProperty("hrmPassword");
            new MobElementObjs(driver, test)
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
