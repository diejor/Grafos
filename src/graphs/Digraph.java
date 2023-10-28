package graphs;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import grafos.edges.Diedge;
import grafos.traversal.UpcomingVertex;
import grafos.traversal.GraphTraversal.Frontier;
import grafos.traversal.GraphTraversal.Order;


public abstract class Digraph<V, E extends Diedge<V>> extends AbstractGraph<V, E> {
    public Digraph() {
        super();
    }

    /**
     * Returns the set of edges that are adjacent to the given vertex.
     * @param u The vertex given.
     */
    public abstract Collection<Diedge<V>> incidentEdges(V u);

    public int inDegree(V u) {
        return incidentEdges(u).size();
    }

    public int outDegree(V u) {
        return adjacentEdges(u).size();
    }

    /**
     *  Returns the transposed graph of this graph. The transposed graph is the graph
     *  with all edges reversed.
     */
    public abstract Digraph<V, E> transposed();

    public Set<List<V>> kosaraju() {
        Set<List<V>> sccs = new HashSet<>();

        Digraph<V, E> transposed = transposed();

        stream(
            Frontier.DFS,
            Order.PREORDER, 
            new HashSet<>(),
            true
            )
            .forEach(cmpStream -> {
                Set<V> cmp = cmpStream.map(UpcomingVertex::vertex).collect(Collectors.toSet());
                Set<List<V>> sccsInCmp = new HashSet<>();


                while (!cmp.isEmpty()) {
                    V explore = cmp.iterator().next();
                    Set<V> visited = new HashSet<>();
                    sccsInCmp.add(
                        transposed.stream(
                            explore,
                            Frontier.DFS,
                            Order.PREORDER,
                            visited,
                            true
                        )
                        .map(UpcomingVertex::vertex)
                        .collect(Collectors.toList())
                    );
                    cmp.removeAll(visited);
                }

                sccs.addAll(sccsInCmp);
            });

            return sccs;
    }
}
