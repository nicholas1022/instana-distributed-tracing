package main;

import java.util.*;

public class Graph {
    private Map<String, Map<String, Integer>> edgeMap;

    public Graph() {
        this.edgeMap = new HashMap<>();
    }

    public void addEdge(String edge) {
        String from = edge.substring(0, 1);
        String to = edge.substring(1, 2);
        int weight = Integer.parseInt(edge.substring(2));

        edgeMap.putIfAbsent(from, new HashMap<>());
        edgeMap.get(from).put(to, weight);
    }

    public Integer getLatency(String from, String to) {
        return edgeMap.getOrDefault(from, Collections.emptyMap()).get(to);
    }

//    public Set<Character> getVertices() {
//        Set<Character> vertices = new HashSet<>(adjacencyMap.keySet());
//        for (Map<Character, Integer> edges : adjacencyMap.values()) {
//            vertices.addAll(edges.keySet());
//        }
//        return vertices;
//    }

    public Set<String> getNeighbors(String node) {
        return new HashSet<>(edgeMap.getOrDefault(node, Collections.emptyMap()).keySet());
    }
}
