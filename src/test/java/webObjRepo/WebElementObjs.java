package webObjRepo;

import baseclassTest.BaseclassWeb;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.windows.WindowsDriver;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import mobObjRepo.desktopPages.HrmLoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;

import static io.restassured.RestAssured.given;

public class WebElementObjs extends BaseclassWeb {

    public WebElementObjs(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "(.//input[@id='username'])")
    private WebElement eleUserName;

    public WebElementObjs enterUserName(String UserName) {
        enterText(eleUserName, UserName);
        return this;
    }

    @FindBy(how = How.XPATH, using = "(.//input[@id='inputPassword'])")
    private WebElement elepassWord;

    public WebElementObjs enterpassWord(String PassWord) {
        enterText(elepassWord, PassWord);
        return this;
    }

    @FindBy(how = How.XPATH, using = "(.//button[@class='btn btn-primary btn-lg btn-block'])")
    private WebElement eleLogin;

    public WebElementObjs clickLogin() {
        click(eleLogin);
        return this;
    }

    @FindBy(how = How.XPATH, using = "(.//a[contains(text(),'Projects')])")
    private WebElement eleProjects;

    public WebElementObjs clickProjects() {
        click(eleProjects);
        return this;
    }

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Create Project')]")
    private WebElement elecreateProjects;

    public WebElementObjs clickcreateProjects() {
        click(elecreateProjects);
        return this;
    }

    @FindBy(how = How.XPATH, using = "(.//input[@name='projectName'])")
    private WebElement eleprojectsName;

    public WebElementObjs enterprojectsName(String projectName) {
        enterText(eleprojectsName, projectName);
        return this;
    }

    @FindBy(how = How.XPATH, using = "(.//input[@name='createdBy'])")
    private WebElement elecreatedBy;

    public WebElementObjs entercreatedBy(String projectName) {
        enterText(elecreatedBy, projectName);
        return this;
    }

    @FindBy(how = How.XPATH, using = "(.//select[@name='status'])[2]")
    private WebElement eleprojecStatus;

    public WebElementObjs clickprojecStatus() {
        Select select = new Select(eleprojecStatus);
        select.selectByValue("Created");
        click(eleprojecStatus);
        return this;
    }

    @FindBy(how = How.XPATH, using = "//input[@class='btn btn-success']")
    private WebElement eleaddProject;
    public WebElementObjs clickaddProject() {
        click(eleaddProject);
        return this;
    }

    @FindBy(how = How.XPATH, using = "//input[@placeholder='Search by Project Id']")
    private WebElement elesearchProject;
    public MobileElementObjs entersearchProject(String projectName) {
        enterText(elesearchProject, projectName);

        extractProjectName(projectName);

        String dbresponse = db.sendQueryGetColumnData("select * from project", "project_name", projectName).toString();

        if(dbresponse.contains(projectName)) {
            System.out.println("Project Name is present in the DB: " + projectName);
        }else{
            System.out.println("Project Name was not present in the DB");
        }
        return new MobileElementObjs(driver, test);
    }

    /*@FindBy(how = How.XPATH, using = "//input[@placeholder='Search by Project Id']")
    private WebElement baseURI;
    public WebElementObjs apiExtractProjectName(String ProjectName) {
        try {
            RestAssured.baseURI = "http://106.51.90.215:8084";
            Response response = given()
                    .contentType("application/json")
                    .when()
                    .get("/projects")
                    .then()
                    .statusCode(200)
                    .extract().response();
            String projectname = response.jsonPath().getString("projectName");

            System.out.println(response.asString());

            if (projectname.contains(ProjectName)) {
                System.out.println("Validating from API Project Name: " + ProjectName);
                reportStep("API Response for Project Name", "PASS");
            } else {
                System.out.println("Project Name was not present");
            }
        } catch (Exception e) {
            reportStep("Project Name was not present", "FAIL");
        }
        return this;
    }*/

    /*@FindBy(how = How.XPATH, using = "//input[@placeholder='Search by Project Id']")
    private WebElement elelaunchApp;
    public WebElementObjs launchMobile_Apps() {
        driver=launchApp("Android","RZ8T5144KRA","13.0","local","bs_app_path");
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)),this);
        return this;
    }

    @FindBy(how = How.XPATH, using ="//android.widget.EditText[@resource-id='com.tyss.rmgyantra:id/EmailAddress']")
    private WebElement eleUsername;

    public WebElementObjs enterMobUsername(String Username) {
        enterText(eleUsername, Username);
        return this;
    }

    @FindBy(how = How.XPATH, using ="//android.widget.EditText[@resource-id='com.tyss.rmgyantra:id/Password']")
    private WebElement elePassword;
    public WebElementObjs enterMobPassword(String Password) {
        enterText(elePassword, Password);
        return this;
    }

    @FindBy(how = How.XPATH, using ="//android.widget.Button[@resource-id='com.tyss.rmgyantra:id/signIn']")
    private WebElement elesignIn;
    public WebElementObjs clickMobsignIn() {
        click(elesignIn);
        return this;
    }

    @FindBy(how = How.ID, using ="com.tyss.rmgyantra:id/navigation_bar_item_small_label_view")
    private WebElement eleMobprojects;
    public WebElementObjs clickMobprojects() {
        click(eleMobprojects);
        return this;
    }

    @FindBy(how = How.ID, using ="com.tyss.rmgyantra:id/searchEditText")
    private WebElement eleMobSearchprojects;
    public WebElementObjs enterMobSearchprojects(String ProjectName) {
        enterText(eleMobSearchprojects, ProjectName);
        return this;
    }

    @FindBy(how = How.ID, using ="com.tyss.rmgyantra:id/projectNameTextView")
    private WebElement eleMobVerifyprojects;
    public WebElementObjs enterMobVerifyprojects(String ProjectName) {
        enterText(eleMobVerifyprojects, ProjectName);
        String str = eleMobVerifyprojects.getText();
        Assert.assertEquals(str, ProjectName);
        return this;
    }*/

    /*@FindBy(how = How.XPATH, using = "//input[@placeholder='Search by Project Id']")
    private WebElement elelaunchDesktop;
    public WebElementObjs launchDesktop() {
        driver=launchApp("Windows","WindowsPC","","local","");
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)),this);
        return this;
    }
    @FindBy(how = How.XPATH, using = "//*[@AutomationId='username']")
    private WebElement eleDTUsername;
    public WebElementObjs enterDTUsername(String Username) {
        hardWait(2000);
        enterText(eleDTUsername, Username);
        return this;
    }

    @FindBy(how = How.XPATH, using = "//*[@AutomationId='inputPassword']")
    private WebElement eleDTPassword;
    public WebElementObjs enterDTPassword(String Password) {
        hardWait(2000);
        enterText(eleDTPassword, Password);
        return this;
    }

    @FindBy(how = How.NAME, using = "Sign in")
    private WebElement eleDTsignIn;
    public WebElementObjs clickDTsignIn() {
        hardWait(2000);
        click(eleDTsignIn);
        return this;
    }*/
}
