package grafos.traversal;

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import grafos.edges.AbstractEdge;

public abstract class CollectionVisitor<V, E extends AbstractEdge<V>> {
    public abstract UpcomingVertex<V, E> consume();

    public abstract void add(UpcomingVertex<V, E> upcoming);

    public abstract boolean isEmpty();

    public abstract int size();

    public static <V, E extends AbstractEdge<V>> CollectionVisitor<V, E> of(
            Collection<UpcomingVertex<V, E>> collection) {
        if (collection instanceof Queue)
            return new QueueVisitor<>((Queue<UpcomingVertex<V, E>>) collection);
        else if (collection instanceof Stack)
            return new StackVisitor<>((Stack<UpcomingVertex<V, E>>) collection);
        else if (collection instanceof PriorityQueue)
            return new PriorityQueueVisitor<>((PriorityQueue<UpcomingVertex<V, E>>) collection);
        else
            throw new IllegalArgumentException("Unsupported collection type");
    }

}

class QueueVisitor<V, E extends AbstractEdge<V>> extends CollectionVisitor<V, E> {
    private final Queue<UpcomingVertex<V, E>> queue;

    public QueueVisitor(Queue<UpcomingVertex<V, E>> queue) {
        this.queue = queue;
    }

    @Override
    public UpcomingVertex<V, E> consume() {
        return queue.poll();
    }

    @Override
    public void add(UpcomingVertex<V, E> upcoming) {
        queue.add(upcoming);
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public int size() {
        return queue.size();
    }
}

class StackVisitor<V, E extends AbstractEdge<V>> extends CollectionVisitor<V, E> {
    public final Stack<UpcomingVertex<V, E>> stack;

    public StackVisitor(Stack<UpcomingVertex<V, E>> stack) {
        this.stack = stack;
    }

    @Override
    public UpcomingVertex<V, E> consume() {
        return stack.pop();
    }

    @Override
    public void add(UpcomingVertex<V, E> upcoming) {
        stack.push(upcoming);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }
}

class PriorityQueueVisitor<V, E extends AbstractEdge<V>> extends CollectionVisitor<V, E> {
    private final PriorityQueue<UpcomingVertex<V, E>> priorityQueue;

    public PriorityQueueVisitor(PriorityQueue<UpcomingVertex<V, E>> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    @Override
    public UpcomingVertex<V, E> consume() {
        return priorityQueue.poll();
    }

    @Override
    public void add(UpcomingVertex<V, E> upcoming) {
        priorityQueue.add(upcoming);
    }

    @Override
    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }

    @Override
    public int size() {
        return priorityQueue.size();
    }
}
