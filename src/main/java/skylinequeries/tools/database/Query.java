package skylinequeries.tools.database;

import skylinequeries.rtree.geometry.Geometries;
import skylinequeries.rtree.geometry.Point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vinh Nguyen
 */
public class Query {
    public static double QUERY_PROCESSING_TIME = 0.0;
    private Connection connection;

    public Query(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Queries the database to get all rows from a table,
     * construct points from these rows and add these points to a list.
     * We also measure the execution time of query.
     *
     * @param tableName the table name
     * @return a list of points
     */
    public List<Point> getAllRecords(final String tableName) {
        PreparedStatement statement = null;
        List<Point> points = new ArrayList<>();

        String query0 = "SET profiling = 1";
        String query1 = "SELECT * FROM " + String.format("%s", "`".concat(tableName).concat("`"));
        String query2 = "SHOW PROFILES";

        try {
            statement = getConnection().prepareStatement(query0);
            statement.executeQuery();

            statement = getConnection().prepareStatement(query1);
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next()) {
                points.add(Geometries.point(resultSet1.getDouble("x"), resultSet1.getDouble("y")));
            }

            statement = getConnection().prepareStatement(query2);
            ResultSet resultSet2 = statement.executeQuery();
            resultSet2.last();
            QUERY_PROCESSING_TIME = resultSet2.getDouble("Duration");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return points;
    }
}
