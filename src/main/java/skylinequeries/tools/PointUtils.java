package skylinequeries.tools;

import skylinequeries.rtree.Entry;
import skylinequeries.rtree.RTree;
import skylinequeries.rtree.geometry.Geometries;
import skylinequeries.rtree.geometry.Point;
import skylinequeries.tools.stdlib.In;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vinh Nguyen
 */
public class PointUtils implements Immutable {

    /**
     * Enumeration indicating the type of input data file.
     */
    public enum FileType {
        TEXT, CSV
    }

    /**
     * Prevents outsiders from instantiating the class.
     */
    private PointUtils() {

    }

    /**
     * x-coordinate.
     */
    private static double x = 0.0;

    /**
     * y-coordinate.
     */
    private static double y = 0.0;

    /**
     * Input stream used to read the data file.
     */
    private static In in = null;

    /**
     * Checks the correctness of the file path.
     *
     * @param filePath the data path
     */
    private static void checkFilePath(final String filePath) {
        try {
            in = new In(filePath);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Reads CSV file containing coordinates of 2D points.
     */
    private static void readCsv() {
        String[] str = in.readLine().split(",");
        x = Double.parseDouble(str[0]);
        y = Double.parseDouble(str[1]);
    }

    /**
     * Reads TEXT file containing coordinates of 2D points.
     */
    private static void readText() {
        x = in.readDouble();
        y = in.readDouble();
    }

    /**
     * Gets the list of points used to populate the Rtree from file.
     *
     * @param dataPath the path to the data file
     * @param type     the type of the data file
     * @return the      list containing points read from data file
     */
    public static List<Point> readPointsFromFile(final String dataPath, final FileType type) {
        List<Point> points = new ArrayList<>();
        checkFilePath(dataPath);
        assert in != null;
        switch (type) {
            case TEXT:
                while (!in.isEmpty()) {
                    readText();
                    points.add(Geometries.point(x, y));
                }
                break;
            case CSV:
                while (!in.isEmpty()) {
                    readCsv();
                    points.add(Geometries.point(x, y));
                }
                break;
            default:
                break;
        }
        return points;
    }

    /**
     * Scans the data file for coordinates of points, creates new points and inserts them
     * into a Rtree.
     *
     * @param dataPath the path to the data file
     * @param rTree    the Rtree
     * @param type     the type of the data file
     * @return the Rtree populated with points read from data file
     */
    public static RTree<Object, Point> getPointsFromFile(final String dataPath, RTree<Object, Point> rTree,
                                                         final FileType type) {
        checkFilePath(dataPath);
        assert in != null;
        switch (type) {
            case TEXT:
                while (!in.isEmpty()) {
                    readText();
                    rTree = rTree.add(new Object(), Geometries.point(x, y));
                }
                break;
            case CSV:
                while (!in.isEmpty()) {
                    readCsv();
                    rTree = rTree.add(new Object(), Geometries.point(x, y));
                }
                break;
            default:
                break;
        }
        return rTree;
    }

    /**
     * Gets points for RTree from a database table.
     *
     * @param rTree     the Rtree to construct
     * @param tableName the database table name
     * @return the Rtree populated with points read from database table
     */
    public static RTree<Object, Point> getPointsFromDB(RTree<Object, Point> rTree, final String tableName) {
        List<Point> points = DB_QUERY.getAllRecords(tableName);
        for (Point point : points) rTree = rTree.add(new Object(), point);
        return rTree;
    }

    /**
     * Gets the list of points used to populate the Rtree form database table.
     *
     * @param tableName the database table name
     * @return the list containing points read from database table
     */
    public static List<Point> getPointsFromDB(final String tableName) {
        return Immutable.DB_QUERY.getAllRecords(tableName);
    }

    /**
     * Converts a list of Rtree entries to a list of points.
     *
     * @param entries the list of entries to convert
     * @return the corresponding list of points
     */
    public static List<Point> entriesToList(final List<Entry<Object, Point>> entries) {
        List<Point> points = new ArrayList<>();
        if (!entries.isEmpty())
            for (Entry<Object, Point> entry : entries)
                points.add(Geometries.point(entry.geometry().x(), entry.geometry().y()));

        return points;
    }
}
