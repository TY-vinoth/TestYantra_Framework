package projectTests.apiTest;

import apiUtility.apiWrappers;
import baseclassTest.BaseclassAPI;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class TC001_ApiTesting extends BaseclassAPI {

    @BeforeTest//Reporting
    public void setValues() {
        testCaseName = "Create a new Incident (REST)";
        testDescription = "Create a new Incident and Verify";
        testNodes = "Incident";
        authors = "Vinoth";
        category = "REST";
        dataFileName = "TC001";
        dataFileType = "JSON";
    }

    @Test()
    public void getProjectName() {
        RestAssured.baseURI = "http://106.51.90.215:8084";
        Response response = given()
                .contentType("application/json")
                .when()
                .get("/projects")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(response.prettyPrint());
    }
}
