package grafos.traversal;

import grafos.edges.AbstractEdge;

public class UpcomingVertex<V, E extends AbstractEdge<V>> {
    public final V srcVertex;
    public final E srcEdge;
    public final V vertex;

    public UpcomingVertex(V srcV, E srcE, V v) {
        this.srcVertex = srcV;
        this.srcEdge = srcE;
        this.vertex = v;
    }

    public V vertex() {
        return vertex;
    }

    public E sourceEdge() {
        return srcEdge;
    }

    public V sourceVertex() {
        return srcVertex;
    }
}