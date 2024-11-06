package main.service;

import main.util.Graph;
import main.model.TraceHopState;

import java.util.*;

public class TraceService implements TraceLatencyInterface, MaxHopsTraceInterface, ExactHopsTraceInterface, ShortestTraceInterface {
    private Graph graph;

    public TraceService(Graph graph) {
        this.graph = graph;
    }

    @Override
    public String traceLatency(String trace) {
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

    @Override
    public int countTraceUnderMaxHop(String start, String end, int maxHops) {
        return countTraceUnderMaxHop(start, end, maxHops, 0);
    }

    private int countTraceUnderMaxHop(String current, String end, int maxHops, int currentHops) {
        if (currentHops > maxHops) {
            return 0;
        }

        int count = 0;
        if (currentHops > 0 && current.equalsIgnoreCase(end)) {
            count++;
        }

        for (String next : graph.getNeighbors(current)) {
            count += countTraceUnderMaxHop(next, end, maxHops, currentHops + 1);
        }
        return count;
    }

    @Override
    public int countTraceByExactHops(String start, String end, int exactHops) {
        if (exactHops == 0) {
            return start.equalsIgnoreCase(end) ? 1 : 0;
        }

        int count = 0;
        for (String next : graph.getNeighbors(start)) {
            count += countTraceByExactHops(next, end, exactHops - 1);
        }
        return count;
    }

    @Override
    public int shortestLength(String start, String end) {
        PriorityQueue<TraceHopState> pq = new PriorityQueue<>(Comparator.comparingInt(TraceHopState::getLatency));
        Set<String> visited = new HashSet<>();

        pq.offer(new TraceHopState(start, 0, 0));
        while (!pq.isEmpty()) {
            TraceHopState current = pq.poll();

            if (end.equalsIgnoreCase(current.getNode()) && current.getHop() != 0) {
                return current.getLatency();
            }

            if (!visited.add(current.getNode())) {
                continue;
            }

            for (String next : graph.getNeighbors(current.getNode())) {
                int newLatency = current.getLatency() + graph.getLatency(current.getNode(), next);
                pq.offer(new TraceHopState(next, newLatency, current.getHop() + 1));
            }

        }

        return -1;
    }

    public int countTraceUnderLatency(String start, String end, int maxLatency) {
        return countTraceUnderLatency(start, end, maxLatency, 0, !start.equalsIgnoreCase(end));
    }

    private int countTraceUnderLatency(String current, String end, int maxLatency, int currentLatency, boolean canEnd) {
        if (currentLatency >= maxLatency) {
            return 0;
        }

        int count = 0;
        if (current.equalsIgnoreCase(end) && canEnd) {
            count = 1;
        }

        for (String next : graph.getNeighbors(current)) {
            int newLatency = currentLatency + graph.getLatency(current, next);
            count += countTraceUnderLatency(next, end, maxLatency, newLatency, true);
        }

        return count;
    }
}
