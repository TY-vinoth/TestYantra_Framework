package projectTests.mobileTest;

import baseclassTest.BaseclassMob;
import mobObjRepo.MobElementObjs;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TC001_Logo_Is_Clickable extends BaseclassMob {

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
    public void MobElement() {
        new MobElementObjs(driver, test)
                .clickChild_one();
    }
}
