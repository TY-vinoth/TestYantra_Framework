package dataProvider.bean.testenv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestEnvPojo {

    @SerializedName("web")
    @Expose
    private Web web;

    public Web getWeb() {
        return web;
    }

    public void setWeb(Web web) {
        this.web = web;
    }
}
