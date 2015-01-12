package skylinequeries.tools.database;

import org.junit.Test;
import skylinequeries.tools.Immutable;
import skylinequeries.rtree.geometry.Geometries;
import skylinequeries.rtree.geometry.Point;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestDatabaseUtils implements Immutable {

    @Test
    public void testOpenDatabaseConnection() {
        Connection connection = DBConnection.open();
        try {
            assertEquals(connection.getCatalog(), "skyline");
            assertEquals(connection.isClosed(), false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCloseDatabaseConnection() {
        Connection connection = DBConnection.open();
        try {
            assertEquals(connection.isClosed(), false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.close(connection);
        try {
            assertEquals(connection.isClosed(), true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAllRowsFromDatabaseTable() {
        List<Point> list = Immutable.DB_QUERY.getAllRecords("points-2d-13-10");

        assertEquals(list.size(), 12);
        assertEquals(list.get(0), Geometries.point(2.0, 10.0));
        assertEquals(list.get(list.size() - 1), Geometries.point(8.0, 3.0));
    }

    @Test
    public void testCountTheNumberOfRowsInDatabaseTable() {
        int counter = Immutable.DB_QUERY.getNumberOfRecords("points-2d-13-10");
        assertEquals(counter, 12);
    }
}
