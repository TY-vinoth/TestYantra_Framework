package JIRA;

import com.github.wnameless.json.flattener.JsonFlattener;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class jiraTicketCreation implements ITestListener {

    public static Properties prop;
    public static String uName="";
    public static String authToken="";
    public static String URI="";


    public Map<String, Object> createJiraTicket(ITestResult result, String bugSummary, String description) {

        prop = new Properties();
        try {
            prop.load(Files.newInputStream(new File("./src/main/resources/config.properties").toPath()));
            uName = prop.getProperty("JIRA_UserName");
            authToken = prop.getProperty("JIRA_API_Token");
            URI = prop.getProperty("JIRA_URI");
        }catch (Exception ignored){

        }
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        String auth = new String(Base64.getEncoder().encode(new String(uName+":"+authToken).getBytes()));
        headers.put("Authorization", "Basic " + auth);

        String projectKey = "TES"; // Ensure this is the correct project key

        String body = "{\n" +
                "    \"fields\": {\n" +
                "       \"project\": { \"key\": \"" + projectKey + "\" },\n" +
                "       \"summary\": \"" + bugSummary + "\",\n" +
                "       \"description\": \"" + description + "\",\n" +
                "       \"issuetype\": { \"name\": \"Task\" }\n" +
                "   }\n" +
                "}";

        Map<String, Object> responseMap = new HashMap<>();
        try {
            RestAssured.baseURI = URI;
            RestAssured.useRelaxedHTTPSValidation();
            RequestSpecification requestSpecification = RestAssured.given().request().headers(headers).body(body);
            Response response = requestSpecification.post("rest/api/2/issue/");
            responseMap = JsonFlattener.flattenAsMap(response.asString());
            System.out.println("JIRA Ticket Created, Ticket ID: " + responseMap.get("self"));
        } catch (Exception e) {
            System.out.println("Failed to create JIRA Ticket");
            e.printStackTrace();
        }
        return responseMap;
    }

    /*public static void main(String[] args) {
        jiraTicketCreation jiraIntegration = new jiraTicketCreation();
        Map<String, Object> response = jiraIntegration.createJiraTicket(result,"Testing","Successfull");
        System.out.println("Response: " + response);
    }*/

}