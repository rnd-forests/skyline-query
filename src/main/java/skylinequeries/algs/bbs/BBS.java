package skylinequeries.algs.bbs;

import skylinequeries.rtree.*;
import skylinequeries.rtree.geometry.Point;
import skylinequeries.tools.logger.FileLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Branch and Bound skyline algorithm.
 *
 * @author Kien Hoang + Vinh Nguyen
 */
public class BBS extends FileLogger {

    /**
     * Keeps track of the number of accessed nodes.
     */
    public static long NODE_ACCESSES = 0;

    /**
     * Logger helpers for algorithm.
     */
    private static final String className = BBS.class.getName();
    private static final String executionMethod = "BBS.execute()";

    /**
     * The Rtree constructed from input points.
     */
    private RTree<Object, Point> rTree;

    /**
     * Constructor.
     *
     * @param rTree the constructed Rtree.
     */
    public BBS(RTree<Object, Point> rTree) {
        this.rTree = rTree;
    }

    /**
     * Checks if a heap element is dominated by an entry.
     * Because the final skyline points are only contained in
     * children of entries in the Rtree, we just need to check
     * the domination condition between a heap element and an entry.
     *
     * @param a the heap element
     * @param b the entry
     * @return true of a is dominated by b, false otherwise
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
     * @param a       the heap element
     * @param entries the set of entries
     * @return true if a is dominated by some entry in entries, false otherwise
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

        StringBuilder s = new StringBuilder();

        if (rTree.isEmpty()) {
            System.err.println("RTree has no nodes!\n");
            return null;
        }

        List<Entry<Object, Point>> skylineEntries = new ArrayList<>();
        PriorityQueue<BBSHeapElement> priorityQueue = new PriorityQueue<>();

        priorityQueue.add(new BBSHeapElement(rTree.root().get(), true));
        NODE_ACCESSES++;

        s.append("Added root to heap...\n");
        s.append("\t└──>Root: ").append(rTree.root().get().geometry().toString()).append("\n\n");

        while (!priorityQueue.isEmpty()) {

            BBSHeapElement head = priorityQueue.poll();

            if (!isDominatedInSet(head, skylineEntries)) {
                if (head.getNode() instanceof NonLeaf) {
                    s.append("Head is a NonLeaf.\n");
                    s.append("\t└──>Head: ").append(head.getNode().geometry().toString()).append("\n");
                    NonLeaf<Object, Point> intermediateNode = (NonLeaf<Object, Point>) head.getNode();
                    s.append("\t└──>Looping through head children.\n");
                    for (Node<Object, Point> child : intermediateNode.children()) {
                        NODE_ACCESSES++;
                        s.append("\t\t└──>Increment the accessed nodes.\n");
                        s.append("\t\t└──>Current child: ").append(child.geometry().toString()).append("\n");
                        BBSHeapElement element = new BBSHeapElement(child, true);
                        if (!isDominatedInSet(element, skylineEntries)) {
                            priorityQueue.add(element);
                            s.append("\t\t\t└──>Added to the heap.\n");
                        }
                        else s.append("\t\t\t└──>Not added to the heap.\n");
                    }
                }
                else if (head.getNode() instanceof Leaf) {
                    s.append("Head element is a Leaf.\n");
                    s.append("\t└──>Head: ").append(head.getNode().geometry().toString()).append("\n");
                    Leaf<Object, Point> intermediateNode = (Leaf<Object, Point>) head.getNode();
                    s.append("\t└──>Looping through head entries.\n");
                    for (Entry<Object, Point> entry : intermediateNode.entries()) {
                        NODE_ACCESSES++;
                        s.append("\t\t└──>Increment the accessed nodes.\n");
                        s.append("\t\t└──>Current entry: ").append(entry.geometry().toString()).append("\n");
                        BBSHeapElement element = new BBSHeapElement(entry, false);
                        if (!isDominatedInSet(element, skylineEntries)) {
                            priorityQueue.add(element);
                            s.append("\t\t\t└──>Added to the heap.\n");
                        }
                        else s.append("\t\t\t└──>Not added to the heap.\n");
                    }
                }
                else {
                    skylineEntries.add(head.getEntry());
                    s.append("└──>Added head's entry to the skyline list.\n");
                    s.append("\t└──>SKYLINE ENTRY: ").append(head.getEntry().toString()).append("\n");
                }
            }
            else s.append("Head has been discarded.\n");

            s.append("\n\n");
        }

        s.append("\n\nAlgorithm terminated.\n");
        bbsLog(s.toString());

        return skylineEntries;
    }

    /**
     * Logging for BBS algorithm.
     *
     * @param message the log message
     */
    private void bbsLog(String message) {
        super.log(message, className, executionMethod);
    }
}
