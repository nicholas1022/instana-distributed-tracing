package instana.model;

import java.util.*;

public class TraceGraph implements GraphInterface {

    private Map<String, Map<String, Integer>> edgeMap;

    public TraceGraph() {
        this.edgeMap = new HashMap<>();
    }

    @Override
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

    @Override
    public Set<String> getNeighbors(String node) {
        return new HashSet<>(edgeMap.getOrDefault(node, Collections.emptyMap()).keySet());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof TraceGraph)) {

            return false;
        }

        TraceGraph other = (TraceGraph) obj;

        return  (this.edgeMap == null && other.edgeMap == null) || (this.edgeMap != null && this.edgeMap.equals(other.edgeMap));
    }

}
