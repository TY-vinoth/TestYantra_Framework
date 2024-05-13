package gitLab;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.models.Issue;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.Properties;

public class BugReporter implements ITestListener  {

    public static Properties prop;
    public static String GITLAB_URL="";
    public static String PERSONAL_ACCESS_TOKEN="";
    public static String PROJECT_ID="";

    public static Map<String, Object> createGitlabTicket(ITestResult result, String testCaseName, String errorMessage) {

        prop = new Properties();
        try {
            prop.load(Files.newInputStream(new File("./src/main/resources/config.properties").toPath()));
            GITLAB_URL = prop.getProperty("GitLabUrl");
            PERSONAL_ACCESS_TOKEN = prop.getProperty("gitLab_API_Token");
            PROJECT_ID = prop.getProperty("GitLab_ProjectID");
        }catch (Exception ignored){

        }
        try {
            GitLabApi gitLabApi = new GitLabApi(GITLAB_URL, PERSONAL_ACCESS_TOKEN);
            Issue issue = gitLabApi.getIssuesApi().createIssue(PROJECT_ID, testCaseName, errorMessage + "\n\n");
            System.out.println("Issue created: " + issue.getWebUrl());
        } catch (Exception e) {
            System.err.println("Failed to report bug to GitLab: " + e.getMessage());
        }
        return null;
    }
}
