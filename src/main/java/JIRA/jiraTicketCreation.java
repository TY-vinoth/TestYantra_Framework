package JIRA;

import com.github.wnameless.json.flattener.JsonFlattener;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.ITestListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class jiraTicketCreation implements ITestListener {

    public Map<String, Object> createJiraTicket(String bugSummary, String description) {
        String URI = "https://vinothkumar-e.atlassian.net";
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        //headers.put("Authorization", "ATATT3xFfGF0Bui--ht1vIcNruEIiVWf5XU6gba_LrT-GTXjPiY63bBaSNbIn97kRMJKSQ0-I7rMA6rf2UqWsmx5-rsKo74eGR1r0lHvNpl15IEHpiq9SLquA6NtAcQnQ6gSOKL2irdPmzsaPHIBTXNt3217V6U8Y78p80RIyMF9HkfggeB-ckY=700EF9FF");

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

    public static void main(String[] args) {
        jiraTicketCreation jiraIntegration = new jiraTicketCreation();
        Map<String, Object> response = jiraIntegration.createJiraTicket("Bug Summary", "Bug Description");
        System.out.println("Response: " + response);
    }

}