package graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import grafos.edges.Diedge;

public class IntegerAdjList extends Digraph<Integer, Diedge<Integer>> {
    List<Integer>[] adjList;

    public IntegerAdjList(int n) {
        adjList = new List[n];
        for (int i = 0; i < n; i++)
            adjList[i] = new ArrayList<>();
    }

    public IntegerAdjList(List<Integer>[] adjList) {
        this.adjList = adjList;
    }

    @Override
    public Collection<Integer> vertices() {
        return IntStream.range(0, adjList.length)
            .mapToObj(Integer::valueOf)
            .collect(Collectors.toSet());
    }

    @Override
    public Collection<Diedge<Integer>> edges() {
        return IntStream.range(0, adjList.length)
            .mapToObj(u -> adjList[u].stream()
                .map(v -> new Diedge<>(u, v))
                .collect(Collectors.toSet()))
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }

    @Override
    public Collection<Diedge<Integer>> incidentEdges(Integer u) {
        return adjList[u].stream()
            .map(v -> new Diedge<>(u, v))
            .collect(Collectors.toSet());
    }

    @Override
    public IntegerAdjList transposed() {
        IntegerAdjList transposed = new IntegerAdjList(adjList.length);
        for (int v = 0; v < adjList.length; v++)
            for (int u : adjList[v])
                transposed.adjList[u].add(v);
        
        return transposed;
    }   

    @Override
    public Collection<Diedge<Integer>> adjacentEdges(Integer u) {
        return adjList[u].stream()
            .map(v -> new Diedge<>(u, v))
            .collect(Collectors.toSet());
    }
}
