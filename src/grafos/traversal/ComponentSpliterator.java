package grafos.traversal;

import java.util.Collection;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Supplier;

import graphs.AbstractGraph;
import grafos.edges.AbstractEdge;

public abstract class ComponentSpliterator<V, E extends AbstractEdge<V>> 
extends GraphTraversal<V, E> implements Spliterator<UpcomingVertex<V,E>>
{
    protected final CollectionVisitor<V, E> frontier;

    public ComponentSpliterator(
            AbstractGraph<V, E> graph,
            V root,
            Supplier<Collection<UpcomingVertex<V, E>>> frontierSupplier,
            Set<V> visited,
            boolean checkVisited
    ) {
        super(graph, visited, checkVisited);
        this.frontier = CollectionVisitor.of(frontierSupplier.get());

        this.frontier.add(new UpcomingVertex<>(null, null, root));
    }

    @Override
    public Spliterator<UpcomingVertex<V, E>> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return frontier.size();
    }
    
    @Override
    public int characteristics() {
        return Spliterator.DISTINCT | Spliterator.NONNULL | Spliterator.ORDERED;
    }

    public static <V, E extends AbstractEdge<V>> ComponentSpliterator<V, E> of(
            AbstractGraph<V, E> graph,
            V root,
            Frontier frontier,
            Order order,
            Set<V> visited,
            boolean checkVisited
    ) {
        Supplier<Collection<UpcomingVertex<V, E>>> frontierSupplier = getFrontierSupplier(frontier);
        switch (order) {
            case PREORDER:
                return new ComponentPreorderSpliterator<>(graph, root, frontierSupplier, visited, checkVisited);
            case POSTORDER:
                return new ComponentPostorderSpliterator<>(graph, root, frontierSupplier, visited, checkVisited);
            default:
                throw new IllegalArgumentException("Invalid order: " + order);
        }
    }
}
