package skylinequeries.rtree;

import skylinequeries.rtree.geometry.Geometry;

import java.util.List;

import static java.util.Collections.min;
import static skylinequeries.rtree.Comparators.*;

/**
 * Uses minimal area increase to select a node from a list.
 */
public final class SelectorMinimalAreaIncrease implements Selector {

    @SuppressWarnings("unchecked")
    @Override
    public <T, S extends Geometry> Node<T, S> select(Geometry g, List<? extends Node<T, S>> nodes) {
        return min(nodes, compose(areaIncreaseComparator(g.mbr()), areaComparator(g.mbr())));
    }
}
