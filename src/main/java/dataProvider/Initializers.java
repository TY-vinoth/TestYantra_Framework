package dataProvider;

import dBUtility.JDBCconnection;


import java.io.BufferedWriter;
import java.util.List;

public class Initializers {

    protected static ThreadLocal<List<String>> failAnalysisThread = new ThreadLocal<>();
    protected static String executionType = "local";
    protected static String defectLog = "";
    protected static boolean exceptionStatus = false;

    public static JDBCconnection db = new JDBCconnection();
    public static ThreadLocal<BufferedWriter> fw;
}
