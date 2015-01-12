package skylinequeries.algs.nn;

import skylinequeries.rtree.Entry;
import skylinequeries.rtree.RTree;
import skylinequeries.rtree.geometry.Geometries;
import skylinequeries.rtree.geometry.Point;
import skylinequeries.tools.logger.FileLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Nearest Neighbor Algorithm.
 *
 * @author Vinh Nguyen + Kien Hoang
 */
public class NN extends FileLogger {

    /**
     * Keeps track of the number of accessed nodes.
     * (Currently not implemented)
     */
    public static long NODE_ACCESSES = 0;

    /**
     * Logger helpers for algorithm.
     */
    private static final String className = NN.class.getName();
    private static final String executionMethod = "NN.execute()";

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

        StringBuilder s = new StringBuilder();

        if (rTree.isEmpty()) {
            System.err.println("Rtree has no nodes!");
            return null;
        }

        List<Entry<Object, Point>> skylineEntries = new ArrayList<>();
        Stack<Point> todoList = new Stack<>();

        s.append("Finding the first nearest neighbor to origin.\n");
        List<Entry<Object, Point>> list = rTree.nearest(origin, MAX_COORDINATE_VALUE, 1)
                .toList().toBlocking().singleOrDefault(null);

        Entry<Object, Point> firstNN = list.get(0);
        s.append("\t└──>First nearest neighbor: ").append(firstNN.geometry().toString()).append("\n");
        skylineEntries.add(firstNN);
        s.append("\t└──>Add first nearest neighbor to skyline list.\n");
        todoList.push(Geometries.point(firstNN.geometry().x(), MAX_COORDINATE_VALUE));
        s.append("\t└──>Add Point(").append(firstNN.geometry().x()).append(", ").append(MAX_COORDINATE_VALUE).append(")").append(" to to-do list.\n");
        todoList.push(Geometries.point(MAX_COORDINATE_VALUE, firstNN.geometry().y()));
        s.append("\t└──>Add Point(").append(MAX_COORDINATE_VALUE).append(", ").append(firstNN.geometry().y()).append(")").append(" to to-do list.\n");

        s.append("\n\n-->@@EVALUATING THE TO-DO LIST@@<---\n\n");

        while (!todoList.empty()) {
            Point p = todoList.pop();
            s.append("└──>Get first point in to-do list.\n");
            List<Entry<Object, Point>> nnl = rTree.boundedNNSearch(origin, Geometries.rectangle(origin, p.x(), p.y()), 1)
                    .toList().toBlocking().singleOrDefault(null);
            s.append("└──>Performing bounded nearest neighbor search.\n");

            if (!nnl.isEmpty()) {
                s.append("\t└──>Found a new nearest neighbor.\n");
                Entry<Object, Point> nn = nnl.remove(0);
                s.append("\t\t└──>Nearest neighbor: ").append(nn.geometry().toString()).append("\n");
                skylineEntries.add(nn);
                s.append("\t\t└──>Add this nearest neighbor to skyline list.\n");
                todoList.push(Geometries.point(nn.geometry().x(), p.y()));
                s.append("\t\t└──>Add Point(").append(nn.geometry().x()).append(", ").append(p.y()).append(")").append(" to to-do list.\n");
                todoList.push(Geometries.point(p.x(), nn.geometry().y()));
                s.append("\t\t└──>Add Point(").append(p.x()).append(", ").append(nn.geometry().y()).append(")").append(" to to-do list.\n");
            }
            else s.append("\t└──>Bounded nearest neighbor search returns no elements.\n");

            s.append("\n\n");
        }

        s.append("\n\nAlgorithm terminated.\n");
        nnLog(s.toString());

        return skylineEntries;
    }

    /**
     * Logging for NN algorithm.
     *
     * @param message the log message
     */
    private void nnLog(String message) {
        super.log(message, className, executionMethod);
    }
}



