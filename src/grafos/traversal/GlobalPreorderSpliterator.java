package grafos.traversal;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import graphs.AbstractGraph;
import grafos.edges.AbstractEdge;

public class GlobalPreorderSpliterator<V, E extends AbstractEdge<V>> extends GlobalSpliterator<V, E> {

    public GlobalPreorderSpliterator(
            AbstractGraph<V, E> graph,
            Supplier<Collection<UpcomingVertex<V, E>>> frontierSupplier,
            Set<V> visited,
            boolean checkVisited
    ) {
        super(graph, frontierSupplier, visited, checkVisited);
    }

    @Override
    public boolean tryAdvance(Consumer<? super Stream<UpcomingVertex<V, E>>> action) {
        if (toExplore.isEmpty())
            return false;
        
        V root = toExplore.iterator().next();
        toExplore.remove(root);

        action.accept(
            stream(new ComponentPreorderSpliterator<>(
                graph, 
                root, 
                frontierSupplier, 
                visited, 
                checkVisited)));

        toExplore.removeAll(visited);

        return true;
    }
}
