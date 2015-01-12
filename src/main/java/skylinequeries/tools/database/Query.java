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

    /**
     * Measures the query execution time.
     */
    public static double QUERY_PROCESSING_TIME = 0.0;

    /**
     * Database connection.
     */
    private Connection connection;

    /**
     * Constructor.
     *
     * @param connection the connection to the database
     */
    public Query(Connection connection) {
        this.connection = connection;
    }

    /**
     * Queries the database to get all rows from a table,
     * construct points from these rows and add them to a list.
     * We also measure the execution time of query.
     *
     * @param tableName the table name
     * @return a list of points
     */
    public synchronized List<Point> getAllRecords(final String tableName) {

        PreparedStatement statement = null;
        List<Point> points = new ArrayList<>();

        String query0 = String.format("%s", "SET profiling = 1");
        String query1 = String.format("%s%s", "SELECT * FROM ", ("`").concat(tableName).concat("`"));
        String query2 = String.format("%s", "SHOW PROFILES");

        try {
            statement = connection.prepareStatement(query0);
            statement.executeQuery();

            statement = connection.prepareStatement(query1);
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next())
                points.add(Geometries.point(resultSet1.getDouble("x"), resultSet1.getDouble("y")));

            statement = connection.prepareStatement(query2);
            ResultSet resultSet2 = statement.executeQuery();
            resultSet2.last();
            QUERY_PROCESSING_TIME = resultSet2.getDouble("Duration");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return points;
    }

    /**
     * Counts the number of rows in a database table.
     *
     * @param tableName table name
     * @return number of rows in this table
     */
    public synchronized int getNumberOfRecords(final String tableName) {

        PreparedStatement statement = null;
        String query = String.format("%s%s", "SELECT COUNT(id) AS size FROM ", ("`").concat(tableName).concat("`"));
        int count = 0;

        try {
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) count = resultSet.getInt("size");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count;
    }
}
