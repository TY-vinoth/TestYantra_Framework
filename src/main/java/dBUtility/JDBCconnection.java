package dBUtility;

import listenerUtils.ReporterManager;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.jcraft.jsch.Session;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCconnection extends ReporterManager {

    private static Connection connection = null;
    private static Session session = null;
    private static BasicDataSource dataSource;
    private static int connectionPort = 3306;
    private static final int openConnections = 10;
    private static final int idleConnections = 3;
    private static final long connectionWaitTime = -1;

    static {
        dataSource = new BasicDataSource();
        try {
            String connectionUrl="";
            dataSource.setUrl(connectionUrl);
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setMaxTotal(openConnections);
            dataSource.setMaxIdle(idleConnections);
            dataSource.setMaxWaitMillis(connectionWaitTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static DataSource getDataSource() {
        return dataSource;
    }
    public static Connection connect() throws SQLException {
        if (connection == null || connection.isClosed()) try {
            connection = getDataSource().getConnection();
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

}
