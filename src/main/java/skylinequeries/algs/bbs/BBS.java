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
     * Because the final skyline points are only contained in
     * entries of the Rtree, we just need to check the domination
     * condition between a heap element and an entry.
     * Note that a heap element can contain either intermediate node or entry.
     *
     * @param a     the heap element
     * @param b     the entry
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

        // Check if the RTree is empty or not. If it is the case,
        // we output an error message, and return null
        if (rTree.isEmpty()) {
            System.err.println("RTree has no nodes!\n");
            return null;
        }

        // An array list to hold skyline entries
        List<Entry<Object, Point>> skylineEntries = new ArrayList<>();

        // A priority queue (heap) to hold heap elements
        PriorityQueue<BBSHeapElement> priorityQueue = new PriorityQueue<>();

        // Gets the root of the RTree and inserts it into the priority queue
        priorityQueue.add(new BBSHeapElement(rTree.root().get(), true));

        // Increments the number of accessed nodes
        NODE_ACCESSES++;

        // Evaluates the priority queue as long as it is not empty
        while (!priorityQueue.isEmpty()) {

            // Gets the head of the priority queue
            BBSHeapElement head = priorityQueue.poll();

            // If the head is dominated by some entries in the skylineEntries list,
            // we just discard it. Otherwise, we check for the domination condition
            // with children of the node contained in head element
            if (!isDominatedInSet(head, skylineEntries)) {
                // Head element is a NonLeaf
                if (head.getNode() instanceof NonLeaf) {
                    // Gets the node inside the head element
                    NonLeaf<Object, Point> intermediateNode = (NonLeaf<Object, Point>) head.getNode();
                    // Evaluates each child (an intermediate node) of the node contained in head element
                    for (Node<Object, Point> child : intermediateNode.children()) {
                        // Increments the number of accessed nodes
                        NODE_ACCESSES++;
                        // Creates new heap element using current child (node)
                        BBSHeapElement element = new BBSHeapElement(child, true);
                        // If this heap element is not dominated by any elements
                        // in the skylineEntries list, we add it to the priority
                        // queue as its children may contain skyline entries
                        if (!isDominatedInSet(element, skylineEntries))
                            priorityQueue.add(element);
                    }
                }
                // Head element is a Leaf
                else if (head.getNode() instanceof Leaf) {
                    // Gets the node inside the head element
                    Leaf<Object, Point> intermediateNode = (Leaf<Object, Point>) head.getNode();
                    // Evaluates each child (an entry) of the node contained in head element
                    for (Entry<Object, Point> entry : intermediateNode.entries()) {
                        // Increments the number of accessed nodes
                        NODE_ACCESSES++;
                        // Creates new heap element using current child (entry)
                        BBSHeapElement element = new BBSHeapElement(entry, false);
                        // If this heap element is not dominated by any elements
                        // in the skylineEntries list, we add it to the priority
                        // queue as it may be a skyline entry
                        if (!isDominatedInSet(element, skylineEntries))
                            priorityQueue.add(element);
                    }
                }
                // If head element is not a NonLeaf or a Leaf, then it
                // can only contain an entry, we just need to insert this
                // entry into skylineEntries list
                else skylineEntries.add(head.getEntry());
            }
        }

        // Returns the list of skyline entries
        return skylineEntries;
    }
}
