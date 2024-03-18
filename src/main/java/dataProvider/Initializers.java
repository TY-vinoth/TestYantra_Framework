package dataProvider;

import dBUtility.JDBCconnection;
import dataProvider.bean.TestEnv;

public class Initializers {
    protected static ThreadLocal<TestEnv> envThreadLocal = new ThreadLocal<>();

    public static TestEnv tEnv() {
        return envThreadLocal.get();
    }
    public static JDBCconnection db = new JDBCconnection();

}
