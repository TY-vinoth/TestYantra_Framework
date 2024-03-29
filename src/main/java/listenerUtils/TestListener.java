package listenerUtils;

import JIRA.jiraTicketCreation;
import listenerUtils.ReporterManager;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class TestListener extends ReporterManager implements IAnnotationTransformer, ITestListener, IRetryAnalyzer {

    int counter = 0;
    int retryLimit = 1;

    public boolean retry(ITestResult result) {

        if (counter < retryLimit) {
            System.out.println("******************************************************************************************");
            System.out.println("Retrying test Execution has applied : " + result.getName() + " with " + result.getMethod().getTestClass() + "status " + " for the " + (retryLimit+1) + " time(s).");
            System.out.println("******************************************************************************************");
            counter++;
            return true;
        }
        return false;
    }


    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(TestListener.class);
    }


    public void onFinish(ITestContext context) {
        Set<ITestResult> failedTests = context.getFailedTests().getAllResults();
        for (ITestResult temp : failedTests) {
            ITestNGMethod method = temp.getMethod();
            if (context.getFailedTests().getResults(method).size() > 1) {
                failedTests.remove(temp);
            } else {
                if (context.getPassedTests().getResults(method).size() > 0) {
                    failedTests.remove(temp);
                }
            }
        }
        Set<ITestResult> skippedTests = context.getSkippedTests().getAllResults();
        for (ITestResult temp : skippedTests) {
            ITestNGMethod method = temp.getMethod();
            if (context.getSkippedTests().getResults(method).size() > 1) {
                skippedTests.remove(temp);
            }
        }
    }

    public void onTestStart(ITestResult result) {
    }

    public void onTestSuccess(ITestResult result) {
    }

    public void onTestFailure(ITestResult result) {
        Map<String, Object> response = jira.createJiraTicket(result,testCaseName,testDescription);
        System.out.println("JIRA Ticket Details:" + response);
        reportStep(testCaseName,"FAIL", false);
    }

    public void onTestSkipped(ITestResult result) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onStart(ITestContext context) {
    }
}
