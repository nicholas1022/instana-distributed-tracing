package main;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class TraceSolver {
    private final Graph graph;

    public TraceSolver(Graph graph) {
        this.graph = graph;
    }

    public String calculateTraceLatency(String trace) {
        int totalLatency = 0;
        String[] services = trace.split("-");
        for (int i = 0; i < services.length - 1; i++) {
            String from = services[i];
            String to = services[i + 1];
            Integer weight = graph.getLatency(from, to);
            if (weight == null) {
                return "NO SUCH TRACE";
            }
            totalLatency += weight;
        }
        return String.valueOf(totalLatency);
    }

    public int countTracesWithMaxHops(String start, String end, int maxHops) {
        return countTracesWithHopsHelper(start, end, maxHops, 0);
    }

    private int countTracesWithHopsHelper(String current, String end, int maxHops, int currentHops) {
        if (currentHops > maxHops) {
            return 0;
        }
        if (currentHops > 0 && current.equalsIgnoreCase(end)) {
            return 1;
        }

        int count = 0;
        for (String next : graph.getNeighbors(current)) {
            count += countTracesWithHopsHelper(next, end, maxHops, currentHops + 1);
        }
        return count;
    }

    public int countTracesWithExactHops(String start, String end, int exactHops) {
        if (exactHops == 0) {
            return start.equalsIgnoreCase(end) ? 1 : 0;
        }

        int count = 0;
        for (String next : graph.getNeighbors(start)) {
            count += countTracesWithExactHops(next, end, exactHops - 1);
        }
        return count;
    }

    public int shortestLength(String start, String end) {
        PriorityQueue<TraceIterationState> pq = new PriorityQueue<>(Comparator.comparingInt(TraceIterationState::getLatency));
        Set<String> visited = new HashSet<>();

        pq.offer(new TraceIterationState(start, 0, 0));
        while (!pq.isEmpty()) {
            TraceIterationState current = pq.poll();

            if (end.equalsIgnoreCase(current.getNode()) && current.getHop() != 0) {
                return current.getLatency();
            }

            if (!visited.add(current.getNode())) {
                continue;
            }

            for (String next : graph.getNeighbors(current.getNode())) {
                int newLatency = current.getLatency() + graph.getLatency(current.getNode(), next);
                pq.offer(new TraceIterationState(next, newLatency, current.getHop() + 1));
            }

        }

        return -1;
    }

    public int countTracesWithMaxLatency(String start, String end, int maxLatency) {
        return countTracesUnderLatencyHelper(start, end, maxLatency, 0, !start.equalsIgnoreCase(end));
    }

    private int countTracesUnderLatencyHelper(String current, String end, int maxLatency, int currentLatency, boolean canEnd) {
        if (currentLatency >= maxLatency) {
            return 0;
        }

        int count = 0;
        if (current.equalsIgnoreCase(end) && canEnd) {
            count = 1;
        }

        for (String next : graph.getNeighbors(current)) {
            int newLatency = currentLatency + graph.getLatency(current, next);
            count += countTracesUnderLatencyHelper(next, end, maxLatency, newLatency, true);
        }

        return count;
    }
}
