package grafos.function;

import java.util.Collection;
import java.util.Map;

import grafos.edges.AbstractEdge;

public class SearchState<V, E extends AbstractEdge<V>, L> {
    public final V vertex;
    public final E adjEdge;
    public final Collection<V> frontier;
    public final Map<V, V> parent;
    public final Map<V, L> labels;

    public SearchState(V vertex, E adjEdge, Collection<V> frontier, Map<V, V> parent, Map<V, L> labels) {
        this.vertex = vertex;
        this.adjEdge = adjEdge;
        this.frontier = frontier;
        this.parent = parent;
        this.labels = labels;
    }
}
