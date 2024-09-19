package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbUtils {
    public static Connection getConnection(String url, String user, String password) throws Exception {
        return DriverManager.getConnection(url, user, password);
    }

    public static void executeQuery(Connection connection, String query) throws Exception {
        Statement statement = connection.createStatement();
        statement.execute(query);
    }

    public static ResultSet getQueryResult(Connection connection, String query) throws Exception {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static void closeConnection(Connection connection) throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
