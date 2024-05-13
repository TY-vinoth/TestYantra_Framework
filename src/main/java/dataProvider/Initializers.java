package dataProvider;

import JIRA.jiraTicketCreation;
import com.aventstack.extentreports.ExtentTest;
import dBUtility.JDBCconnection;
import gitLab.BugReporter;

import java.io.BufferedWriter;
import java.util.List;

public class Initializers {

    protected static ThreadLocal<List<String>> failAnalysisThread = new ThreadLocal<>();
    protected static String executionType = "local";
    protected static String pipelineExecution = "false";
    protected static boolean exceptionStatus = false;

    public static JDBCconnection db = new JDBCconnection();
    public static jiraTicketCreation jira = new jiraTicketCreation();
    public static BugReporter gitlab = new BugReporter();
    public static ThreadLocal<BufferedWriter> fw;
}
