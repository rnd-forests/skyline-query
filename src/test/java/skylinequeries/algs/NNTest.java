package skylinequeries.algs;

import skylinequeries.tools.PointUtils;
import skylinequeries.tools.Utils;
import skylinequeries.tools.Drawer;
import skylinequeries.algs.nn.NN;
import skylinequeries.rtree.Entry;
import skylinequeries.rtree.RTree;
import skylinequeries.rtree.geometry.Point;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NNTest extends SwingWorker<List<Entry<Object, Point>>, Entry<Object, Point>> {

    private final String dataset;
    private final JTextArea sViewer;

    private RTree<Object, Point> rTree = RTree.star().create();
    private double cpuTime;
    private List<Entry<Object, Point>> sEntries;

    private List<Point> points;
    private List<Point> sPoints;

    public NNTest(String dataset, JTextArea sViewer) {
        this.dataset = dataset;
        this.sViewer = sViewer;
    }

    @Override
    protected List<Entry<Object, Point>> doInBackground() throws Exception {
        rTree = PointUtils.getPointsFromDB(rTree, dataset);
        final NN nn = new NN(rTree);

        final long before = System.nanoTime();
        sEntries = nn.execute();
        final long after = System.nanoTime();
        cpuTime = (double) (after - before) / 1000000000.0;

        points = PointUtils.getPointsFromDB(dataset);
        sPoints = PointUtils.entriesToList(sEntries);

        for (Entry<Object, Point> entry : sEntries)
            publish(entry);

        return sEntries;
    }

    @Override
    protected void process(List<Entry<Object, Point>> foundEntries) {
        int count = 1;
        for (Entry<Object, Point> entry : foundEntries) {
            sViewer.append(String.format("%3d> -- %s", count, entry.geometry().toString()) + "\n\n");
            count++;
        }
    }

    @Override
    protected void done() {
        Utils.console(sEntries, points.size(), cpuTime, NN.NODE_ACCESSES);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(new Drawer(points, sPoints, 0.0, 0.0, 10000.0, 10000.0));
        executor.shutdown();
        System.gc();
    }
}
