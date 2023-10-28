package grafos.edges;

import java.util.Objects;

public class Diedge<V> extends AbstractEdge<V> {

    public Diedge(V from, V to) {
        super(from, to);
    }

    public V tail() {
        return v();
    }

    public V head() {
        return w();
    }

    public boolean isHead(V v) {
        return v.equals(head());
    }

    public boolean isTail(V v) {
        return v.equals(tail());
    }

    /**
     * Returns the end vertex of this edge if {@code u} is the start vertex.
     * 
     * @param u the start vertex
     * @return the end vertex of this edge if {@code u} is the start vertex, null if
     *         {@code u} is the end vertex
     * @throws IllegalArgumentException if {@code u} is not contained in this edge
     * 
     */
    @Override
    public V adj(V u) {
        if (isTail(u))
            return head();
        else if (!isHead(u))
            throw new IllegalArgumentException("Vertex " + u + " is not contained in this edge");
        throw new IllegalArgumentException("Vertex " + u + " is the end vertex of this edge");
    }

    public void transpose() {
        V tail = tail();
        setV(head());
        setW(tail);
    }

    public Diedge<V> transposed() {
        return new Diedge<>(head(), tail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tail(), head());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Diedge<?>))
            return false;
        Diedge<?> other = (Diedge<?>) obj;
        return Objects.equals(tail(), other.tail()) && Objects.equals(head(), other.head());
    }
}