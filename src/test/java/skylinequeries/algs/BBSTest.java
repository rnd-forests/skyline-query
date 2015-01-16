package skylinequeries.algs;

import skylinequeries.algs.bbs.BBS;
import skylinequeries.tools.Drawer;
import skylinequeries.tools.Utilities;
import skylinequeries.rtree.Entry;
import skylinequeries.rtree.RTree;
import skylinequeries.rtree.geometry.Point;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BBSTest extends SwingWorker<List<Entry<Object, Point>>, Entry<Object, Point>> {

    private final String dataset;
    private final JTextArea sViewer;

    private RTree<Object, Point> rTree = RTree.star().create();
    private double cpuTime;
    private List<Entry<Object, Point>> sEntries;

    private List<Point> points;
    private List<Point> sPoints;

    public BBSTest(String dataset, JTextArea sViewer) {
        this.dataset = dataset;
        this.sViewer = sViewer;
    }

    @Override
    protected List<Entry<Object, Point>> doInBackground() throws Exception {
        rTree = Utilities.getPointsFromDB(rTree, dataset);
        final BBS bbs = new BBS(rTree);

        final long before = System.nanoTime();
        sEntries = bbs.execute();
        final long after = System.nanoTime();
        cpuTime = (double) (after - before) / 1000000000.0;

        points = Utilities.getPointsFromDB(dataset);
        sPoints = Utilities.entriesToPoints(sEntries);

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
        Utilities.console(sEntries, points.size(), cpuTime, BBS.NODE_ACCESSES);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(new Drawer(points, sPoints, 0.0, 0.0, 10000.0, 10000.0));
        executor.shutdown();
        System.gc();
    }
}
