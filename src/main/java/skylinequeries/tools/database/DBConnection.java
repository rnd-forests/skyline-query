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

    public static Connection initialize() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Constants.DB_URL,
                    Constants.DB_USERNAME, Constants.DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void terminate(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
