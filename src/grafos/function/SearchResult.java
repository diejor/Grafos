package grafos.function;

import java.util.AbstractMap;

public class SearchResult<V, L> {
    public final V vertex;
    public final V adjacentVertex;
    public final AbstractMap.SimpleEntry<V, V> parentEntry;
    public final AbstractMap.SimpleEntry<V, L> labelEntry;
    public Boolean found;

    public SearchResult(V vertex, V adjacentVertex, AbstractMap.SimpleEntry<V, V> parentEntry,
            AbstractMap.SimpleEntry<V, L> labelEntry, Boolean found) {
        this.vertex = vertex;
        this.adjacentVertex = adjacentVertex;
        this.parentEntry = parentEntry;
        this.labelEntry = labelEntry;
        this.found = found;
    }
}