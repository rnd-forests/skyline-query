package skylinequeries.tools;

import skylinequeries.tools.database.DBConnection;
import skylinequeries.tools.database.Query;

import java.text.SimpleDateFormat;

public interface Immutable {

    String URL = "jdbc:mysql://localhost:3306/skyline"

    String USERNAME = "root";

    String PASSWORD = "nnv8815121993";

    Query DB_QUERY = new Query(DBConnection.open());
    
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

}
