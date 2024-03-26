package dataProvider.bean.remoteenv;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RemoteEnvPojo implements Serializable {
    @SerializedName("execution_type")
    @Expose
    private String execution_type;
    @SerializedName("pipeline_execution")
    @Expose
    private String pipeline_execution;
    @SerializedName("db_config")
    @Expose
    private JsonObject db_config;


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
    public JsonObject getDb_config() {
        return db_config;
    }
    public void setDb_config(JsonObject db_config) {
        this.db_config = db_config;
    }

}
