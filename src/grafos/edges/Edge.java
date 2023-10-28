package grafos.edges;

public class Edge<V> extends AbstractEdge<V> {
    public Edge(V v1, V v2) {
        super(v1, v2);
    }

    @Override
    public V adj(V u) {
        if (u.equals(v()))
            return w();
        else if (u.equals(w()))
            return v();
        else
            throw new IllegalArgumentException("Vertex " + u + " is not contained in this edge");
    }

    @Override
    public int hashCode() {
        return v().hashCode() + w().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Edge<?>))
            return false;
        Edge<?> other = (Edge<?>) obj;
        return (v().equals(other.v()) && w().equals(other.w()))
                || (v().equals(other.w()) && w().equals(other.v()));
    }
}