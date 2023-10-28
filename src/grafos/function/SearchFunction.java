package grafos.function;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.function.*;

import grafos.edges.AbstractEdge;

/*
 * Functional interface for search algorithms. It is a function that takes 
 * {@code SearchState} and {@code SearchUtils} as parameters and returns a {@code SearchResult}.
 * It is used to implement search algorithms such as BFS, DFS, Dijkstra, etc. with the 
 * archetypal {@code searchPath} function in {@code AbstractGraph}. 
 */
public interface SearchFunction<V, E extends AbstractEdge<V>, L> {
    public SearchResult<V, L> search(SearchState<V, E, L> state, SearchUtils<V, E,L> utils);
}