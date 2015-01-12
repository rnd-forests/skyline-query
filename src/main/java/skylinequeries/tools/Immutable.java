package skylinequeries.tools;

import skylinequeries.tools.database.DBConnection;
import skylinequeries.tools.database.Query;

import java.text.SimpleDateFormat;

/**
 * @author Vinh Nguyen
 */
public interface Immutable {

    /**
     * Database connection url.
     */
    String URL = "jdbc:mysql://localhost:3306/skyline";

    /**
     * Database username.
     */
    String USERNAME = "root";

    /**
     * Database password.
     */
    String PASSWORD = "nnv8815121993";

    /**
     * Database query handler.
     */
    Query DB_QUERY = new Query(DBConnection.open());

    /**
     * Simple data time format.
     */
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
