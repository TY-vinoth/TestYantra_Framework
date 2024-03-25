package dBUtility;

import listenerUtils.ReporterManager;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.jcraft.jsch.Session;
import org.testng.Assert;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class JDBCconnection extends ReporterManager {

    private static Connection connection = null;
    private Properties prop;
    private static Session session = null;
    private static BasicDataSource dataSource;
    private static int connectionPort = 3333;
    private static final int openConnections = 10;
    private static final int idleConnections = 3;
    private static final long connectionWaitTime = -1;
    private static String dbUserName = "root@%";
    private static String dbPassword = "root";
    private static String dbHost = "106.51.90.215";
    private static String dbName = "projects";
    private static String connectionUrl="jdbc:mysql://" + dbHost + ":" + connectionPort + "/" + dbName;



    public static DataSource getDataSource() {

        try {
            connectionUrl="jdbc:mysql://" + dbHost + ":" + connectionPort + "/" + dbName;
            dataSource.setUrl(connectionUrl);
            connection = DriverManager.getConnection(connectionUrl, dbUserName, dbPassword);
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setMaxTotal(openConnections);
            dataSource.setMaxIdle(idleConnections);
            dataSource.setMaxWaitMillis(connectionWaitTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    public static Connection connect() throws SQLException {
        if (connection == null || connection.isClosed()) try {
            connection = getDataSource().getConnection();
            System.out.println("##### Connection Established ######"+connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public String sendQuery(String query, String columnLabel) {
        String resultArray = null;
        Statement stmt = null;
        try {
            connection = connect();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                resultArray = rs.getString(columnLabel);
            }
            logDBData(query, resultArray);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                if (session != null && session.isConnected()) {
                    session.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultArray;
    }

    public ArrayList<String> sendQueryReturnList(String query, String value) {
        ArrayList resultArray = new ArrayList();

        Statement stmt = null;
        try {
            connection = connect();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                resultArray.add(rs.getString(value));
            }
            logDBData(query, String.valueOf(rs));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                if (session != null && session.isConnected()) {
                    session.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultArray;
    }

    public String updateQuery(String query) {
        Statement stmt = null;
        try {
            connection = connect();
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
            logDBData(query, "Updated in DB");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                if (session != null && session.isConnected()) {
                    session.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return query;
    }

    public String sendQuery(String query, int columnIndex) {
        String rs1 = null;
        Statement stmt = null;
        try {
            connection = connect();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                rs1 = rs.getString(columnIndex);
            }
            logDBData(query, rs1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                if (session != null && session.isConnected()) {
                    session.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rs1;
    }

    public Map<String, Object> sendQueryReturnMap(String query) {
        Statement stmt = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<>();
        try {
            connection = connect();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int count = rsMetaData.getColumnCount();
            String columnName = null;
            while (rs.next()) {
                for (int i = 1; i <= count; i++) {
                    columnName = rsMetaData.getColumnName(i);
                    map.put(columnName, rs.getString(columnName));
                }
            }
            logDBData(query, String.valueOf(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                if (session != null && session.isConnected()) {
                    session.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public List<Map<String, Object>> sendQueryReturnTableAsMap(String query) {
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> table = new ArrayList<>();
        try {
            connection = connect();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int count = rsMetaData.getColumnCount();
            String columnName = null;
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 1; i <= count; i++) {
                    columnName = rsMetaData.getColumnName(i);
                    map.put(columnName, rs.getString(columnName));
                }
                table.add(map);
            }
            logDBData(query, String.valueOf(table));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                if (session != null && session.isConnected()) {
                    session.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return table;
    }


    public List<Map<String, Object>> sendQuerygetColumnData(String query, String columnName) {
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> table = new ArrayList<>();
        try {
            connection = connect();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int count = rsMetaData.getColumnCount();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 1; i <= count; i++) {
                    columnName = rsMetaData.getColumnName(i);
                    map.put(columnName, rs.getString(columnName));
                }
                table.add(map);
            }
            logDBData(query, String.valueOf(table));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                if (session != null && session.isConnected()) {
                    session.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return table;
    }

    public List<Map<String, Object>> sendQueryGetColumnData(String query, String columnName, String expectedData) {
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> table = new ArrayList<>();
        try {
//            connection = connect();
            connection = DriverManager.getConnection(connectionUrl, dbUserName, dbPassword);
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int count = rsMetaData.getColumnCount();
            while (rs.next()) {
                String columnValue = rs.getString(columnName);
                if(columnValue.equals(expectedData)) {
                    Map<String, Object> map = new HashMap<>();
                    for (int i = 1; i <= count; i++) {
                        String columnNameFromMetaData = rsMetaData.getColumnName(i);
                        Object value = rs.getObject(i);
                        map.put(columnNameFromMetaData, value);
                    }
                    table.add(map);
                }
            }
            logDBData(query, String.valueOf(table));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                if (session != null && session.isConnected()) {
                    session.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return table;
    }

    public boolean GetColumnData(String query, String columnName, String expectedData) {
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> table = new ArrayList<>();
        try {
            connection = connect();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int count = rsMetaData.getColumnCount();
            while (rs.next()) {
                String columnValue = rs.getString(columnName);
                boolean isMatch = columnValue.equals(expectedData);
                Map<String, Object> map = new HashMap<>();
                map.put(columnName, columnValue);
                map.put("Match", isMatch);
                Assert.assertEquals(expectedData, isMatch);
                table.add(map);
            }
            logDBData(query, String.valueOf(table));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                if (session != null && session.isConnected()) {
                    session.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
