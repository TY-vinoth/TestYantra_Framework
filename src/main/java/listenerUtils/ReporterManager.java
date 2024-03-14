package listenerUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Properties;
import java.util.logging.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import dataProvider.Initializers;
import org.testng.Assert;

public class ReporterManager extends Initializers {
	public static ThreadLocal<ExtentTest> extentMethodNode = new ThreadLocal<>();
	private Logger log = Logger.getLogger(this.getClass().getName());
	protected static boolean exceptionStatus = false;

	public ExtentHtmlReporter html;
	public static ExtentReports extent;
	public ExtentTest test, suiteTest;
	public String testCaseName, testNodes, testDescription, category, authors, imagePath;


	public void startResult() {
		html = new ExtentHtmlReporter(System.getProperty("user.dir") + "/reports/result.html");
		html.config().setEncoding("utf-8");
		html.config().setProtocol(Protocol.HTTPS);
		html.config().setDocumentTitle("Automation Report");
		html.config().setReportName("Regression Testing");
		html.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		html.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.attachReporter(html);
	}


	public ExtentTest startTestModule(String testCaseName, String testDescription) {
		suiteTest = extent.createTest(testCaseName, testDescription);
		return suiteTest;
	}

	public ExtentTest startTestCase(String testNodes) {
		test = suiteTest.createNode(testNodes);
		return test;
	}

    public long takeScreenShot() {
        return 0;
    }

    public void reportStep(String desc, String status, boolean bSnap) {

		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));

			imagePath = prop.getProperty("Imagepath");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		MediaEntityModelProvider img = null;
		if (bSnap && !status.equalsIgnoreCase("INFO")) {

			long snapNumber = 1000000L;
			snapNumber = takeScreenShot();
			try {
				if (imagePath == null) {
					img = MediaEntityBuilder.createScreenCaptureFromPath("./../reports/images/" + snapNumber + ".png")
							.build();
				} else {
					img = MediaEntityBuilder.createScreenCaptureFromPath(imagePath + "/" + snapNumber + ".png").build();
				}
			} catch (IOException e) {

			}
		}

		if (status.equalsIgnoreCase("PASS")) {
			test.pass(desc, img);
			test.log(Status.PASS, MarkupHelper.createLabel(" PASSED ", ExtentColor.GREEN));
		}
		else if (status.equalsIgnoreCase("FAIL")) {
			test.fail(desc, img);
			test.log(Status.FAIL, MarkupHelper.createLabel(" FAILED ", ExtentColor.RED));
		}
		else if (status.equalsIgnoreCase("WARNING")) {
			test.warning(desc, img);
			test.log(Status.WARNING, MarkupHelper.createLabel(" WARNING ", ExtentColor.YELLOW));
		}
		else if (status.equalsIgnoreCase("SKIP")) {
			test.warning(desc, img);
			test.log(Status.SKIP, MarkupHelper.createLabel(" SKIP ", ExtentColor.ORANGE));
		}
		else if (status.equalsIgnoreCase("INFO")) {
			test.info(desc);
			test.log(Status.INFO, MarkupHelper.createLabel(" INFO ", ExtentColor.PINK));
		}
	}

	public void reportStep(String desc, String status) {
		System.out.println("Mobile Application : " + desc + " " + status);
		reportStep(desc, status, true);
	}

	public void endResult() {
		if (extent != null) {
			try {
				extent.flush();
			} catch (ConcurrentModificationException c) {
				try {
					Thread.sleep(100);
					extent.flush();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void endTestcase(){
		extent.removeTest(test);
	}

	public void logDBData(String query, String dbResponse) {
		log.info("Query : " + query);
		if (dbResponse == null) {
			dbResponse = "No Data found or returned null";
		}
		String m = "<details><summary><font color=\"green\"><b>DBQuery</b></font></summary> " + MarkupHelper.createCodeBlock(query, CodeLanguage.valueOf(dbResponse)).getMarkup() + "</details>";
		extentMethodNode.get().info(m);
	}

	protected void captureException(Exception e) {
		try {
			//log.(ExceptionUtils.getStackTrace(e));
			exceptionStatus = true;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	protected void hardFail() {
		reportStep("FAIL", "Test Exception Occurred");
		Assert.fail("Test Exception Occured");
	}

	public void hardWait(long delay) {
		try {
			double seconds = ((double) delay / 1000);
			if (delay > 0) {
				log.info("\u001b[34m" + "Proceeding with Hard wait !! Please wait for : " + seconds + " Seconds" + "\u001b[34m");
			}
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			captureException(e);
		}
	}
}