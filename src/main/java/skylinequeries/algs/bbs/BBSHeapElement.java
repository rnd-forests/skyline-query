package skylinequeries.algs.bbs;

import skylinequeries.rtree.Entry;
import skylinequeries.rtree.Node;
import skylinequeries.rtree.geometry.Point;

/**
 * @author Vinh Nguyen
 */
public class BBSHeapElement implements Comparable<BBSHeapElement> {
    /**
     * A node in Rtree. In case the current accessed node is a NonLeaf,
     * its children are still intermediate nodes or Leafs.
     */
    private Node<Object, Point> node;

    /**
     * And entry in Rtree. In case the current accessed node is a Leaf,
     * its children are entries which point to the actual data points.
     */
    private Entry<Object, Point> entry;

    /**
     * The mindist of the Rtree entry (the sum of all dimensions).
     */
    private double heapElementMinDist;

    /**
     * Checks if heap element contains node or entry.
     */
    private boolean isNode;

    /**
     * Returns the node contained in the heap element.
     *
     * @return the heap node
     */
    public Node<Object, Point> getNode() {
        return node;
    }

    /**
     * Returns the entry contained in the heap element.
     *
     * @return the heap entry
     */
    public Entry<Object, Point> getEntry() {
        return entry;
    }

    /**
     * Returns a boolean value indicating whether heap element contains node or entry.
     *
     * @return true if node, false if entry
     */
    public boolean isNode() {
        return isNode;
    }

    /**
     * Constructs heap element using a node.
     *
     * @param node   the given node
     * @param isNode the node checker
     */
    public BBSHeapElement(Node<Object, Point> node, boolean isNode) {
        this.node = node;
        this.heapElementMinDist = node.geometry().mbr().x1() + node.geometry().mbr().y1();
        this.isNode = isNode;
    }

    /**
     * Constructs heap element using an entry.
     *
     * @param entry  the given entry
     * @param isNode the node checker
     */
    public BBSHeapElement(Entry<Object, Point> entry, boolean isNode) {
        this.entry = entry;
        this.heapElementMinDist = entry.geometry().x() + entry.geometry().y();
        this.isNode = isNode;
    }

    /**
     * Compares heap elements according to their mindist values.
     *
     * @param o the heap element to compare
     * @return the comparison indicator
     */
    @Override
    public int compareTo(BBSHeapElement o) {
        if (this.heapElementMinDist > o.heapElementMinDist) return 1;
        if (this.heapElementMinDist < o.heapElementMinDist) return -1;
        return 0;
    }
}