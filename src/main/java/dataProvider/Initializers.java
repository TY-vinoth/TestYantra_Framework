package dataProvider;

import JIRA.jiraTicketCreation;
import com.aventstack.extentreports.ExtentTest;
import dBUtility.JDBCconnection;
import dataProvider.bean.testenv.TestEnv;

import java.util.List;

public class Initializers {
    protected static ThreadLocal<TestEnv> envThreadLocal = new ThreadLocal<>();
    protected static ThreadLocal<List<String>> failAnalysisThread = new ThreadLocal<>();


    public static TestEnv tEnv() {
        return envThreadLocal.get();
    }
    public static ThreadLocal<ExtentTest> extentScenarioNode = new ThreadLocal<>();
    public static ThreadLocal<ExtentTest> extentMethodNode = new ThreadLocal<>();
    public static JDBCconnection db = new JDBCconnection();
    public static jiraTicketCreation jira = new jiraTicketCreation();

}
