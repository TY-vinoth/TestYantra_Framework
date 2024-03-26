package dataProvider.bean.testenv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Web {
    @SerializedName("system_os")
    @Expose
    private String systemOs;
    @SerializedName("system_os_version")
    @Expose
    private String systemOsVersion;
    @SerializedName("browser")
    @Expose
    private String browser;
    @SerializedName("headless")
    @Expose
    private String headless;
    @SerializedName("browser_version")
    @Expose
    private String browserVersion;
    @SerializedName("url")
    @Expose
    private String webUrl;

    public String getSystemOs() {
        return systemOs;
    }

    public void setSystemOs(String systemOs) {
        this.systemOs = systemOs;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getHeadless() {
        return headless;
    }

    public void setHeadless(String headless) {
        this.headless = headless;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSystemOsVersion() {
        return systemOsVersion;
    }

    public void setSystemOsVersion(String systemOsVersion) {
        this.systemOsVersion = systemOsVersion;
    }

}
