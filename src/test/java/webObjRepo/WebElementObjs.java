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
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        /*Map<String, String> xpathMap = getXPathMap("id");
        List<String> xpathList = new ArrayList<>(xpathMap.values());
        for (String xpath : xpathList) {
            WebElement userNameElement = driver.findElement(By.xpath(xpath));
            if(xpath.equalsIgnoreCase("username")){
                System.out.println(xpath);
                enterText(userNameElement, UserName);
            }
        }
        String userNameXPath = xpathMap.get("input");
        if (userNameXPath!= null) {
            WebElement userNameElement = driver.findElement(By.xpath(userNameXPath));
            //System.out.println(userNameElement);
            enterText(userNameElement,"rmgy@9999");
        } else {
            System.out.println("XPath not found for input element.");
        }*/
        enterText(eleUserName, UserName);
        return this;
    }

    @FindBy(how = How.XPATH, using = "(.//input[@id='inputPassword'])")
    private WebElement elepassWord;
    public WebElementObjs enterpassWord(String PassWord) {
        /*Map<String, String> xpathMap = getXPathMap("input");
        List<String> xpathList = new ArrayList<>(xpathMap.values());
        for (String xpath : xpathList) {
            WebElement userNameElement = driver.findElement(By.xpath(xpath));
            if(xpath.equalsIgnoreCase("Password")){
                System.out.println(xpath);
                enterText(userNameElement, PassWord);
            }
        }
        String userNameXPath = xpathMap.get("input");
        if (userNameXPath!= null) {
            WebElement userNameElement = driver.findElement(By.xpath(userNameXPath));
            //System.out.println(userNameElement);
            enterText(userNameElement,"rmgy@9999");
        } else {
            System.out.println("XPath not found for input element.");
        }*/
        enterText(elepassWord, PassWord);
        return this;
    }

    @FindBy(how = How.XPATH, using = "(.//button[contains(text(),'Sign in')])")
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
}
