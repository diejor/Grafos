package grafos.traversal;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import graphs.AbstractGraph;
import grafos.edges.AbstractEdge;

public class ComponentPreorderSpliterator<V, E extends AbstractEdge<V>> extends ComponentSpliterator<V, E> {

    public ComponentPreorderSpliterator(
            AbstractGraph<V, E> graph,
            V root,
            Supplier<Collection<UpcomingVertex<V, E>>> frontierSupplier,
            Set<V> visited,
            boolean checkVisited
    ) {
        super(graph, root, frontierSupplier, visited, checkVisited);
    }

    @Override
    public boolean tryAdvance(Consumer<? super UpcomingVertex<V, E>> action) {
        if (frontier.isEmpty())
            return false;

        UpcomingVertex<V, E> upcoming = frontier.consume();

        if (!checkVisited)
            action.accept(upcoming);

        if (visited.contains(upcoming.vertex)) {
            return true;
        }

        if (checkVisited)
            action.accept(upcoming);

        visited.add(upcoming.vertex);

        for (E e : graph.adjacentEdges(upcoming.vertex)) {
            V w = e.adj(upcoming.vertex);
            frontier.add(new UpcomingVertex<>(upcoming.vertex, e, w));
        }

        return true;
    }
}
