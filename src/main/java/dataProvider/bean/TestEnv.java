package dataProvider.bean;



public class TestEnv {
    public String getJsonFilePath() {
        return jsonFilePath;
    }

    // common variables
    private String jsonFilePath;
    private String jsonDirectory;

    public String getJsonDirectory() {
        if (jsonDirectory == null) {
            jsonDirectory = "src/test/resources/TestData";
        }
        return jsonDirectory;
    }

}
