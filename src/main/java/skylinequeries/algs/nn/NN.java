package skylinequeries.algs.nn;

import skylinequeries.rtree.Entry;
import skylinequeries.rtree.RTree;
import skylinequeries.rtree.geometry.Geometries;
import skylinequeries.rtree.geometry.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Nearest Neighbor Algorithm.
 *
 * @author Vinh Nguyen + Kien Hoang
 */
public class NN {

    /**
     * The maximum coordinate value of the data point.
     */
    public static final double MAX_COORDINATE_VALUE = Double.POSITIVE_INFINITY;

    /**
     * The origin.
     */
    public static final Point origin = Geometries.point(0.0, 0.0);

    /**
     * The Rtree constructed from data points.
     */
    private RTree<Object, Point> rTree;

    /**
     * Constructor.
     *
     * @param rTree the constructed Rtree
     */
    public NN(RTree<Object, Point> rTree) {
        this.rTree = rTree;
    }

    /**
     * Performs the NN (Nearest neighbor) algorithm.
     *
     * @return the list containing skyline entries
     */
    public List<Entry<Object, Point>> execute() {

        // Check if the RTree is empty or not. If it is the case,
        // we output an error message, and return null
        if (rTree.isEmpty()) {
            System.err.println("Rtree has no nodes!");
            return null;
        }

        // An array list to hold skyline entries
        List<Entry<Object, Point>> skylineEntries = new ArrayList<>();

        // A stack (to-do list) to hold points which are used to
        // perform bounded nearest neighbors search operation
        Stack<Point> todoList = new Stack<>();

        // Retrieves the first nearest neighbor to the origin
        List<Entry<Object, Point>> list = rTree.nearest(origin, MAX_COORDINATE_VALUE, 1)
                .toList().toBlocking().singleOrDefault(null);

        // Gets the entry from the list and inserts it into skylineEntries list
        Entry<Object, Point> firstNN = list.get(0);
        skylineEntries.add(firstNN);

        // Adds two points after discovering the first nearest neighbor
        // into the to-do list
        todoList.push(Geometries.point(firstNN.geometry().x(), MAX_COORDINATE_VALUE));
        todoList.push(Geometries.point(MAX_COORDINATE_VALUE, firstNN.geometry().y()));

        // Evaluates the to-do list as long as it is not empty
        while (!todoList.empty()) {
            // Gets the point at the top of the to-do list (stack)
            Point p = todoList.pop();

            // Gets one nearest neighbor to the origin with the restriction that
            // the nearest neighbor must be inside the rectangle created by the
            // origin and the point p
            List<Entry<Object, Point>> nnl = rTree.boundedNNSearch(origin, Geometries.rectangle(origin, p.x(), p.y()), 1)
                    .toList().toBlocking().singleOrDefault(null);

            // If we found that nearest neighbor
            if (!nnl.isEmpty()) {
                // Adds that nearest neighbor to the skylineEntries list
                Entry<Object, Point> nn = nnl.remove(0);
                skylineEntries.add(nn);

                // Adds two points after discovering that nearest neighbor
                // into the to-do list
                todoList.push(Geometries.point(nn.geometry().x(), p.y()));
                todoList.push(Geometries.point(p.x(), nn.geometry().y()));
            }
        }

        // Returns the list of skyline entries
        return skylineEntries;
    }
}



