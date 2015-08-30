package skylinequeries.tools;

import skylinequeries.rtree.Entry;
import skylinequeries.rtree.RTree;
import skylinequeries.rtree.geometry.Geometries;
import skylinequeries.rtree.geometry.Point;
import skylinequeries.tools.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vinh Nguyen
 */
public class Utilities implements Constants {
    private Utilities() {
    }

    /**
     * Gets points for RTree from a database table.
     *
     * @param rTree     the Rtree to construct
     * @param tableName the database table name
     * @return the Rtree populated with points read from database table
     */
    public static RTree<Object, Point> constructRTree(RTree<Object, Point> rTree, final String tableName) {
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
    public static List<Point> constructRTree(final String tableName) {
        return Constants.DB_QUERY.getAllRecords(tableName);
    }

    /**
     * Converts a list of Rtree entries to a list of points.
     *
     * @param entries the list of entries to convert
     * @return the corresponding list of points
     */
    public static List<Point> entriesToPoints(final List<Entry<Object, Point>> entries) {
        List<Point> points = new ArrayList<>();
        if (!entries.isEmpty())
            for (Entry<Object, Point> entry : entries)
                points.add(Geometries.point(entry.geometry().x(), entry.geometry().y()));

        return points;
    }

    /**
     * Gets the application memory information in runtime from JVM.
     */
    public static void getMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        System.out.println();
        System.out.println("Java virtual machine memory information");
        System.out.println("\t└──>Used Memory  = " + (runtime.totalMemory() - runtime.freeMemory()) / 1000000 + " MB");
        System.out.println("\t└──>Free Memory  = " + runtime.freeMemory() / 1000000 + " MB");
        System.out.println("\t└──>Total Memory = " + runtime.totalMemory() / 1000000 + " MB");
        System.out.println("\t└──>Max Memory   = " + runtime.maxMemory() / 1000000 + " MB");
    }

    /**
     * Prints algorithms experimental results to the console.
     *
     * @param skyline       list of skyline points
     * @param size          the size of the input data
     * @param executionTime algorithm execution time
     */
    public static void console(final List skyline, final long size,
                               final double executionTime, final long accessedNodes) {
        System.out.println("Algorithm performance");
        System.out.println("\t└──>Total points in dataset        = " + size);
        System.out.println("\t└──>Number of skyline points       = " + skyline.size());
        System.out.println("\t└──>Total time to query the DB (s) = " + Query.QUERY_PROCESSING_TIME);
        System.out.println("\t└──>CPU execution time (s)         = " + executionTime);
        if (accessedNodes != 0)
            System.out.println("\t└──>Number of accessed nodes       = " + accessedNodes + " (" + (float) (accessedNodes * 100.0 / size) + "%)");
        getMemoryInfo();
        System.out.println("--------------------------------\n\n");
    }
}
