package baseclassTest;

import dataProvider.DataInputProvider;
import io.restassured.RestAssured;
import listenerUtils.ReporterManager;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseclassAPI extends ReporterManager {

    public String dataFileName, dataFileType;
    //Reporting
    @BeforeSuite
    public void beforeSuite() {
        startResult();
    }
    //Reporting
    @BeforeClass
    public void beforeClass() {
        //startTestCase(testNodes, testDescription);
    }


    @BeforeMethod
    public void beforeMethod() throws FileNotFoundException, IOException {
        //for reports
        test = startTestModule(testCaseName, testDescription);
        test.assignAuthor(authors);
        test.assignCategory(category);

        Properties prop = new Properties();
        prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));

        RestAssured.authentication = RestAssured.basic(prop.getProperty("username"),prop.getProperty("password"));
        RestAssured.baseURI = "https://"+prop.getProperty("server")+"/"+prop.getProperty("resources")+"/";


    }

    @AfterMethod
    public void afterMethod() {
    }

    @AfterSuite
    public void afterSuite() {
        // report output
        endResult();
    }

    @DataProvider(name="fetchData")
    public  Object[][] getData(){
        if(dataFileType.equalsIgnoreCase("Excel"))
            return DataInputProvider.getSheet(dataFileName);
        else if(dataFileType.equalsIgnoreCase("JSON")){
            Object[][] data = new Object[1][1];
            data[0][0] = new File("./data/"+dataFileName+"."+dataFileType);
            System.out.println(data[0][0]);
            return data;
        }else {
            return null;
        }

    }
}
