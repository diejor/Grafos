package grafos.edges;

/*
 * An edge is a pair of vertices. It is directed in the sense that vertices v
 * and w are ordered, giving a diferent hashCode for (v, w) than for (w, v).
 * Nevertheless, adj() method is undirected. Directed or undirected should be 
 * determined by its implementations, taking into account adj() method.
 */
public abstract class AbstractEdge<V> {
    private V v;
    private V w;

    protected AbstractEdge(V v, V w) {
        this.v = v;
        this.w = w;
    }

    public V v() {
        return v;
    }

    public V w() {
        return w;
    }

    protected void setV(V v) {
        this.v = v;
    }

    protected void setW(V w) {
        this.w = w;
    }

    /*
     * Return the adjacent vertex to u in the edge.
     */
    public abstract V adj(V u);

    /*
     * True if u is one of the vertices of this edge, false otherwise.
     */
    public boolean isIncident(V u) {
        return u.equals(v) || u.equals(w);
    }

    /*
     * Hashcode must always be implemented since it dictates the Set behavior.
     */
    public abstract int hashCode();

    public abstract boolean equals(Object obj);
}