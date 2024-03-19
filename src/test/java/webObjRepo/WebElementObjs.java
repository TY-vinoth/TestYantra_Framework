package webObjRepo;

import baseclassTest.BaseclassWeb;
import com.aventstack.extentreports.ExtentTest;
import dBUtility.JDBCconnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebElementObjs extends BaseclassWeb {

    public String projectName ="";

    public WebElementObjs(RemoteWebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        PageFactory.initElements(driver, this);

    }

    @FindBy(how = How.XPATH, using = "(.//input[@id='username'])")
    private WebElement eleUserName;
    public WebElementObjs enterUserName(String UserName) {
        //db.sendQuery("select * from project","project_name");
        //db.sendQueryGetColumnData("select * from project","project_name","FireFlink_79693");
        //db.sendQueryGetColumnData("select * from project","project_name","FireFlink_79693");
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
    public WebElementObjs entersearchProject(String projectName) {
        String dbresponse="";
        enterText(elesearchProject, projectName);
        dbresponse = db.sendQueryGetColumnData("select * from project","project_name",projectName).toString();
        return this;
    }

}
