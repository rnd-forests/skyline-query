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
        String query = "SELECT * FROM " + String.format("%s", "`".concat(tableName).concat("`"));

        try {
            statement = getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                points.add(Geometries.point(resultSet.getDouble("x"), resultSet.getDouble("y")));
            }
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
