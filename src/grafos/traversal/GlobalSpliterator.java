package grafos.traversal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import graphs.AbstractGraph;
import grafos.edges.AbstractEdge;

public abstract class GlobalSpliterator<V, E extends AbstractEdge<V>>
    extends GraphTraversal<V, E> implements Spliterator<Stream<UpcomingVertex<V, E>>>
{
    protected Stream<UpcomingVertex<V, E>> currentTraversal;
    protected final Supplier<Collection<UpcomingVertex<V,E>>> frontierSupplier;
    protected final Set<V> toExplore;

    public GlobalSpliterator(
            AbstractGraph<V, E> graph,
            Supplier<Collection<UpcomingVertex<V,E>>> frontierSupplier,
            Set<V> visited,
            boolean checkVisited
    ) {
        super(graph, visited, checkVisited);
        this.frontierSupplier = frontierSupplier;
        this.toExplore = new HashSet<>(graph.vertices());
    }

    @Override
    public Spliterator<Stream<UpcomingVertex<V, E>>> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return toExplore.size();
    }

    @Override
    public int characteristics() {
        return Spliterator.DISTINCT | Spliterator.NONNULL | Spliterator.ORDERED;
    }

    public static <V, E extends AbstractEdge<V>> GlobalSpliterator<V, E> of(
            AbstractGraph<V, E> graph,
            Frontier frontier,
            Order order,
            Set<V> visited,
            boolean checkVisited
    ) {
        Supplier<Collection<UpcomingVertex<V,E>>> frontierSupplier = getFrontierSupplier(frontier);
        switch (order) {
            case PREORDER:
                return new GlobalPreorderSpliterator<>(graph, frontierSupplier, visited, checkVisited);
            case POSTORDER:
                return new GlobalPostorderSpliterator<>(graph, frontierSupplier, visited, checkVisited);
            default:
                throw new IllegalArgumentException("Invalid order: " + order);
        }
    }

    public static <V, E extends AbstractEdge<V>> Stream<UpcomingVertex<V, E>> stream(
        Spliterator<UpcomingVertex<V, E>> spliterator
    ) {
        return StreamSupport.stream(spliterator, false);
    }
}
