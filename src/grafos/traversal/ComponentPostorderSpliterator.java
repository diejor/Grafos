package grafos.traversal;

import java.util.Collection;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Supplier;

import graphs.AbstractGraph;
import grafos.edges.AbstractEdge;

public class ComponentPostorderSpliterator<V, E extends AbstractEdge<V>> extends ComponentSpliterator<V, E> {

    private final Stack<UpcomingVertex<V, E>> stack = new Stack<>();

    public ComponentPostorderSpliterator(
            AbstractGraph<V, E> graph,
            V root,
            Supplier<Collection<UpcomingVertex<V, E>>> frontierSupplier,
            Set<V> visited,
            boolean checkVisited
    ) {
        super(graph, root, frontierSupplier, visited, checkVisited);

        stack.push(new UpcomingVertex<>(null, null, root));
    }

    @Override
    public boolean tryAdvance(Consumer<? super UpcomingVertex<V, E>> action) {
        while (!stack.isEmpty()) {
            UpcomingVertex<V, E> upcoming = stack.peek();
            if (visited.contains(upcoming.vertex)) {
                stack.pop();
                action.accept(upcoming);
                return true;
            }
            
            if (!checkVisited)
                action.accept(upcoming);

            visited.add(upcoming.vertex);
            for (E e : graph.adjacentEdges(upcoming.vertex)) {
                V w = e.adj(upcoming.vertex);
            }
        }

        return false;
    }

}
