package skylinequeries.tools;

import skylinequeries.tools.database.DBConnection;
import skylinequeries.tools.database.Query;

/**
 * @author Vinh Nguyen
 */
public interface Constants {
    String URL = "jdbc:mysql://localhost:3306/skyline";
    Query DB_QUERY = new Query(DBConnection.open());
    String USERNAME = "root";
    String PASSWORD = "root";
}
