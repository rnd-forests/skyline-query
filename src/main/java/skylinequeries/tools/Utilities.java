package skylinequeries.tools;

import skylinequeries.rtree.Entry;
import skylinequeries.rtree.RTree;
import skylinequeries.rtree.geometry.Geometries;
import skylinequeries.rtree.geometry.Point;

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
        for (Point point : points) {
            rTree = rTree.add(new Object(), point);
        }
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
        if (!entries.isEmpty()) {
            for (Entry<Object, Point> entry : entries) {
                points.add(Geometries.point(entry.geometry().x(), entry.geometry().y()));
            }
        }

        return points;
    }

    /**
     * Prints algorithms experimental results to the console.
     *
     * @param skyline       list of skyline points
     * @param size          the size of the input data
     * @param executionTime algorithm execution time
     * @param accessedNodes the number of accessed nodes in RTree
     */
    public static String console(final List skyline, final long size,
                                 final long executionTime, final long accessedNodes) {
        StringBuilder console = new StringBuilder();
        Runtime runtime = Runtime.getRuntime();

        console.append("\nAlgorithm performance\n")
                .append("\t+ Dataset = ").append(size).append(" points").append("\n")
                .append("\t+ Skyline = ").append(skyline.size()).append(" points").append("\n")
                .append("\t+ Execution time = ").append(executionTime).append(" ms").append("\n")
                .append("\t+ Accessed nodes = ").append(accessedNodes != 0 ? accessedNodes + " nodes" : "No data available")
                .append("\n").append("Memory information")
                .append("\n").append("\t+ Used Memory = ")
                .append((runtime.totalMemory() - runtime.freeMemory()) / 1000000).append(" MB").append("\n")
                .append("\t+ Free memory = ").append(runtime.freeMemory() / 1000000).append(" MB").append("\n")
                .append("\t+ Total memory = ").append(runtime.totalMemory() / 1000000).append(" MB").append("\n\n");

        return console.toString();
    }
}
