package skylinequeries.rtree;

import skylinequeries.rtree.geometry.Geometry;

import java.util.List;

import static java.util.Collections.min;

public final class SelectorMinimalOverlapArea implements Selector {

    @SuppressWarnings("unchecked")
    @Override
    public <T, S extends Geometry> Node<T, S> select(Geometry g, List<? extends Node<T, S>> nodes) {
        return min(
                nodes,
                Comparators.compose(Comparators.overlapAreaComparator(g.mbr(), nodes), Comparators.areaIncreaseComparator(g.mbr()),
                        Comparators.areaComparator(g.mbr())));
    }

}
