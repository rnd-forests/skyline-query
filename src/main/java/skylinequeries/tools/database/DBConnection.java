package skylinequeries.tools.database;

import skylinequeries.tools.Immutable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Vinh Nguyen
 */
public class DBConnection implements Immutable {

    /**
     * Prevent outsiders from instantiating the class.
     */
    private DBConnection() {

    }

    /**
     * Opens connection to the database.
     *
     * @return new instance of database connection
     */
    public static Connection open() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Immutable.URL,
                    Immutable.USERNAME, Immutable.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Closes connection to the database.
     *
     * @param connection connection to close
     */
    public static void close(Connection connection) {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
