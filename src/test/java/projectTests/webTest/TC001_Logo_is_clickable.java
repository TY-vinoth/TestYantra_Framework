package projectTests.webTest;

import baseclassTest.BaseclassWeb;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webObjRepo.WebElementObjs;

public class TC001_Logo_is_clickable extends BaseclassWeb {

	@Parameters({ "executionType", "browser", "platform", "url" })
	@BeforeTest(alwaysRun = true)
	public void setData() {
		testCaseName = "Homepage logo clickable";
		testDescription = "Ensure the logo exists and the page refreshes once clicked.";
		authors = "Vinoth";
		browserName = "chrome";
		runGroup = "Automation";
	}

	@Test()
	public void WebElementObjs() {
		new WebElementObjs(driver, test)
				.enterPassword("type something***********");
	}
}