package grafos.traversal;

import java.util.Collection;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.function.Supplier;

import graphs.AbstractGraph;
import grafos.edges.AbstractEdge;

public abstract class GraphTraversal<V, E extends AbstractEdge<V>> {
    protected final AbstractGraph<V, E> graph;
    protected final Set<V> visited;
    protected boolean checkVisited;

    public enum Scope {
        GLOBAL,
        COMPONENT;
    }

    public enum Frontier {
        BFS,
        DFS,
        PRIORITY_QUEUE;
    }

    public enum Order {
        PREORDER,
        POSTORDER;
    }

    public GraphTraversal(
            AbstractGraph<V, E> graph,
            Set<V> visited,
            boolean checkVisited
    ) {
        this.graph = graph;
        this.visited = visited;
        this.checkVisited = checkVisited;
    }

    public static <V, E extends AbstractEdge<V>> Supplier<Collection<UpcomingVertex<V, E>>> getFrontierSupplier(Frontier frontier) {
        switch (frontier) {
            case BFS:
                return () -> (Queue<UpcomingVertex<V, E>>) new LinkedList<UpcomingVertex<V,E>>();
            case DFS:
                return () -> (Stack<UpcomingVertex<V, E>>) new Stack<UpcomingVertex<V,E>>();
            case PRIORITY_QUEUE:
                return () -> (PriorityQueue<UpcomingVertex<V, E>>) new PriorityQueue<UpcomingVertex<V,E>>();
            default:
                throw new IllegalArgumentException("Invalid frontier type");
        }
    }
}
