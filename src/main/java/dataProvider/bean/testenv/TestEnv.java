package dataProvider.bean.testenv;

public class TestEnv {

    private String webSystemOS;
    private String webSystemOSVersion;
    private String webBrowser;
    private String webHeadless;
    private String webBrowserVersion;
    private String webUrl;
    private String execution_type;
    private String pipeline_execution;
    private String dbHost;
    private String dbUserName;
    private String dbPassword;
    private String dbSSHHost;
    private String dbSSHUser;
    private String dbSSHFilePath;
    private String dbName;
    private String jsonFilePath;
    private String jsonDirectory;

    public String getWebSystemOS() {
        return webSystemOS;
    }

    public void setWebSystemOS(String webSystemOS) {
        this.webSystemOS = webSystemOS;
    }

    public String getWebSystemOSVersion() {
        return webSystemOSVersion;
    }

    public void setWebSystemOSVersion(String webSystemOSVersion) {
        this.webSystemOSVersion = webSystemOSVersion;
    }

    public String getWebBrowser() {
        return webBrowser;
    }

    public void setWebBrowser(String webBrowser) {
        this.webBrowser = webBrowser;
    }

    public String getWebHeadless() {
        return webHeadless;
    }

    public void setWebHeadless(String webHeadless) {
        this.webHeadless = webHeadless;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWebBrowserVersion() {
        return webBrowserVersion;
    }

    public void setWebBrowserVersion(String webBrowserVersion) {
        this.webBrowserVersion = webBrowserVersion;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbSSHHost() {
        return dbSSHHost;
    }

    public void setDbSSHHost(String dbSSHHost) {
        this.dbSSHHost = dbSSHHost;
    }

    public String getDbSSHUser() {
        return dbSSHUser;
    }

    public void setDbSSHUser(String dbSSHUser) {
        this.dbSSHUser = dbSSHUser;
    }

    public String getDbSSHFilePath() {
        return dbSSHFilePath;
    }

    public void setDbSSHFilePath(String dbSSHFilePath) {
        this.dbSSHFilePath = dbSSHFilePath;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getExecution_type() {
        return execution_type;
    }

    public void setExecution_type(String execution_type) {
        this.execution_type = execution_type;
    }

    public String getPipeline_execution() {
        return pipeline_execution;
    }

    public void setPipeline_execution(String pipeline_execution) {
        this.pipeline_execution = pipeline_execution;
    }

    public String getJsonFilePath() {
        return jsonFilePath;
    }

    public void setJsonFilePath(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
    }

    public String getJsonDirectory() {
        if (jsonDirectory == null) {
            jsonDirectory = "src/test/resources/TestData";
        }
        return jsonDirectory;
    }

    public void setJsonDirectory(String jsonDirectory) {
        this.jsonDirectory = jsonDirectory;
    }


}
