package skylinequeries.tools.database;

import skylinequeries.tools.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Vinh Nguyen
 */
public class DBConnection implements Constants {
    private DBConnection() {
    }

    public static Connection open() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Constants.URL,
                    Constants.USERNAME, Constants.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
