package skylinequeries.algs.bbs;

import skylinequeries.rtree.*;
import skylinequeries.rtree.geometry.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Branch and Bound skyline algorithm.
 *
 * @author Kien Hoang + Vinh Nguyen
 */
public class BBS {

    /**
     * Keeps track of the number of accessed nodes in RTree.
     */
    public static long NODE_ACCESSES = 0;

    /**
     * The Rtree constructed from data points.
     */
    private RTree<Object, Point> rTree;

    /**
     * Constructor.
     *
     * @param rTree the constructed Rtree
     */
    public BBS(RTree<Object, Point> rTree) {
        this.rTree = rTree;
    }

    /**
     * Checks if a heap element is dominated by an entry.
     *
     * @param a     the heap element
     * @param b     the entry
     *
     * @return      true of a is dominated by b, false otherwise
     */
    private boolean isDominated(BBSHeapElement a, Entry<Object, Point> b) {
        if (a.isNode())
            return (b.geometry().x() <= a.getNode().geometry().mbr().x1())
                    && (b.geometry().y() <= a.getNode().geometry().mbr().y1());
        return (b.geometry().x() <= a.getEntry().geometry().mbr().x1())
                && (b.geometry().y() <= a.getEntry().geometry().mbr().y1());
    }

    /**
     * Checks if a heap element is dominated in a set of entries.
     *
     * @param a         the heap element
     * @param entries   the set of entries
     *
     * @return          true if a is dominated by some entry in entries, false otherwise
     */
    private boolean isDominatedInSet(BBSHeapElement a, List<Entry<Object, Point>> entries) {
        for (Entry<Object, Point> entry : entries)
            if (isDominated(a, entry)) return true;
        return false;
    }

    /**
     * Executes the BBS (Branch and Bound Skyline) algorithm.
     *
     * @return the list of skyline entries
     */
    public List<Entry<Object, Point>> execute() {

        if (rTree.isEmpty()) {
            System.err.println("RTree has no nodes!\n");
            return null;
        }

        List<Entry<Object, Point>> skylineEntries = new ArrayList<>();
        PriorityQueue<BBSHeapElement> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new BBSHeapElement(rTree.root().get(), true));

        NODE_ACCESSES++;

        while (!priorityQueue.isEmpty()) {
            BBSHeapElement head = priorityQueue.poll();
            if (!isDominatedInSet(head, skylineEntries)) {
                if (head.getNode() instanceof NonLeaf) {
                    NonLeaf<Object, Point> intermediateNode = (NonLeaf<Object, Point>) head.getNode();
                    for (Node<Object, Point> child : intermediateNode.children()) {
                        NODE_ACCESSES++;
                        BBSHeapElement element = new BBSHeapElement(child, true);
                        if (!isDominatedInSet(element, skylineEntries))
                            priorityQueue.add(element);
                    }
                }
                else if (head.getNode() instanceof Leaf) {
                    Leaf<Object, Point> intermediateNode = (Leaf<Object, Point>) head.getNode();
                    for (Entry<Object, Point> entry : intermediateNode.entries()) {
                        NODE_ACCESSES++;
                        BBSHeapElement element = new BBSHeapElement(entry, false);
                        if (!isDominatedInSet(element, skylineEntries))
                            priorityQueue.add(element);
                    }
                }
                else skylineEntries.add(head.getEntry());
            }
        }

        return skylineEntries;
    }
}
