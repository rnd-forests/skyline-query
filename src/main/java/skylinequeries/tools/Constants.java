package skylinequeries.tools;

import skylinequeries.tools.database.DBConnection;
import skylinequeries.tools.database.Query;

/**
 * @author Vinh Nguyen
 */
public interface Constants {
    String DB_URL = "jdbc:mysql://localhost:3306/skyline";
    String DB_USERNAME = "root";
    String DB_PASSWORD = "root";
    Query DB_QUERY = new Query(DBConnection.initialize());
}
