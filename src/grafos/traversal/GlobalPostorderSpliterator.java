package grafos.traversal;

import java.util.Collection;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import graphs.AbstractGraph;
import grafos.edges.AbstractEdge;

public class GlobalPostorderSpliterator<V, E extends AbstractEdge<V>> extends GlobalSpliterator<V, E> {

    public GlobalPostorderSpliterator(
            AbstractGraph<V, E> graph,
            Supplier<Collection<UpcomingVertex<V, E>>> frontierSupplier,
            Set<V> visited,
            boolean checkVisited
    ) {
        super(graph, frontierSupplier, visited, checkVisited);
    }

    @Override
    public boolean tryAdvance(Consumer<? super Stream<UpcomingVertex<V, E>>> action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tryAdvance'");
    }

    @Override
    public Spliterator<Stream<UpcomingVertex<V,E>>> trySplit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'trySplit'");
    }

    @Override
    public long estimateSize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estimateSize'");
    }

    @Override
    public int characteristics() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'characteristics'");
    }

}
