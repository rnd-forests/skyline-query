package skylinequeries.algs;

import skylinequeries.tools.Utilities;
import skylinequeries.tools.Drawer;
import skylinequeries.algs.nn.NN;
import skylinequeries.rtree.Entry;
import skylinequeries.rtree.RTree;
import skylinequeries.rtree.geometry.Point;

import javax.swing.*;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NNTest extends SwingWorker<List<Entry<Object, Point>>, Entry<Object, Point>> {
    private RTree<Object, Point> rTree = RTree.star().create();
    private List<Entry<Object, Point>> skylineEntries;
    private final String dataset;
    private final JTextArea skylineViewer;
    private List<Point> points;
    private List<Point> skylinePoints;
    private long executionTime;

    public NNTest(String dataset, JTextArea skylineViewer) {
        this.dataset = dataset;
        this.skylineViewer = skylineViewer;
    }

    @Override
    protected List<Entry<Object, Point>> doInBackground() throws Exception {
        rTree = Utilities.constructRTree(rTree, dataset);
        final NN nn = new NN(rTree);

        Instant before = Instant.now();
        skylineEntries = nn.execute();
        Instant after = Instant.now();
        executionTime = Duration.between(before, after).toMillis();

        points = Utilities.constructRTree(dataset);
        skylinePoints = Utilities.entriesToPoints(skylineEntries);

        for (Entry<Object, Point> entry : skylineEntries)
            publish(entry);

        return skylineEntries;
    }

    @Override
    protected void process(List<Entry<Object, Point>> foundEntries) {
        int count = 1;
        for (Entry<Object, Point> entry : foundEntries) {
            skylineViewer.append(String.format("%3d> -- %s", count, entry.geometry().toString()) + "\n\n");
            count++;
        }
    }

    @Override
    protected void done() {
        Utilities.console(skylineEntries, points.size(), executionTime, 0);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(new Drawer(points, skylinePoints, 0.0, 0.0, 10000.0, 10000.0));
        executor.shutdown();
        System.gc();
    }
}
