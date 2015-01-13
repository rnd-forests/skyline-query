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
     * The maximum coordinate value of the point.
     */
    public static final double MAX_COORDINATE_VALUE = Double.POSITIVE_INFINITY;

    /**
     * The origin.
     */
    public static final Point origin = Geometries.point(0.0, 0.0);

    /**
     * The Rtree constructed from input points.
     */
    private RTree<Object, Point> rTree;

    /**
     * Constructor.
     *
     * @param rTree the rtree to store data points
     */
    public NN(RTree<Object, Point> rTree) {
        this.rTree = rTree;
    }

    /**
     * Performs the NN (Nearest neighbor) algorithm.
     *
     * @return the list containing skyline entries
     */
    public synchronized List<Entry<Object, Point>> execute() {

        if (rTree.isEmpty()) {
            System.err.println("Rtree has no nodes!");
            return null;
        }

        List<Entry<Object, Point>> skylineEntries = new ArrayList<>();
        Stack<Point> todoList = new Stack<>();

        List<Entry<Object, Point>> list = rTree.nearest(origin, MAX_COORDINATE_VALUE, 1)
                .toList().toBlocking().singleOrDefault(null);

        Entry<Object, Point> firstNN = list.get(0);
        skylineEntries.add(firstNN);
        todoList.push(Geometries.point(firstNN.geometry().x(), MAX_COORDINATE_VALUE));
        todoList.push(Geometries.point(MAX_COORDINATE_VALUE, firstNN.geometry().y()));

        while (!todoList.empty()) {
            Point p = todoList.pop();
            List<Entry<Object, Point>> nnl = rTree.boundedNNSearch(origin, Geometries.rectangle(origin, p.x(), p.y()), 1)
                    .toList().toBlocking().singleOrDefault(null);

            if (!nnl.isEmpty()) {
                Entry<Object, Point> nn = nnl.remove(0);
                skylineEntries.add(nn);
                todoList.push(Geometries.point(nn.geometry().x(), p.y()));
                todoList.push(Geometries.point(p.x(), nn.geometry().y()));
            }
        }

        return skylineEntries;
    }
}



