package instana.service;

import instana.model.TraceLatencyState;
import instana.util.Graph;
import instana.model.TraceHopState;
import instana.util.InputUtil;

import java.util.*;

public class TraceService implements TraceLatencyInterface, MaxHopsTraceInterface, ExactHopsTraceInterface, ShortestTraceInterface {
    private Graph graph;

    private InputUtil inputUtil;

    public TraceService(Graph graph, InputUtil inputUtil) {
        this.graph = graph;
        this.inputUtil = inputUtil;
    }

    @Override
    public String traceLatency(String trace) {
        int totalLatency = 0;

        String[] services = trace.split("-");
        if(trace.length() <= 1 || services.length <= 1) {
            return "INVALID INPUT";
        }
        for (int i = 0; i < services.length - 1; i++) {
            String start = services[i];
            String end = services[i + 1];
            Integer weight = graph.getLatency(start, end);
            if (weight == null) {
                return "NO SUCH TRACE";
            }
            totalLatency += weight;
        }
        return String.valueOf(totalLatency);
    }

    @Override
    public int countTraceUnderMaxHop(String data) {

        TraceHopState traceHopState = inputUtil.parseHop(data);

        if (traceHopState == null) return -1;

        String start = traceHopState.getOriginNode();
        String end = traceHopState.getDestinationNode();
        int maxHops = traceHopState.getHop();

        if (maxHops < 0) {
            return -1;
        }

        if (!inputUtil.isValidNodeInput(start) || !inputUtil.isValidNodeInput(end)) {
            return -1;
        }

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
    public int countTraceByExactHops(String data) {
        TraceHopState traceHopeState = inputUtil.parseHop(data);
        if (traceHopeState == null) return -1;

        String start = traceHopeState.getOriginNode();
        String end = traceHopeState.getDestinationNode();
        int exactHops = traceHopeState.getHop();

        if (exactHops < 0) {
            return -1;
        }

        if (!inputUtil.isValidNodeInput(start) || !inputUtil.isValidNodeInput(end)) {
            return -1;
        }

        return countTraceByExactHopsHelper(start, end, exactHops);
    }

    public int countTraceByExactHopsHelper(String start, String end, int exactHops) {

        if (exactHops == 0) {
            return start.equalsIgnoreCase(end) ? 1 : 0;
        }

        int count = 0;
        for (String next : graph.getNeighbors(start)) {
            count += countTraceByExactHopsHelper(next, end, exactHops - 1);
        }
        return count;
    }

    @Override
    public int shortestLength(String trace) {
        if (trace == null) return -1;

        String[] services = trace.split("-");
        if (services.length != 2) {
            return -1;
        }
        String start = services[0];
        String end = services[1];

        if (trace.length() <= 1 || services.length <= 1 || !inputUtil.isValidNodeInput(start) || !inputUtil.isValidNodeInput(end)) {
            return -1;
        }

        PriorityQueue<TraceLatencyState> pq = new PriorityQueue<>(Comparator.comparingInt(TraceLatencyState::getLatency));
        Set<String> visited = new HashSet<>();

        pq.offer(new TraceLatencyState(start, end, 0));
        while (!pq.isEmpty()) {
            TraceLatencyState current = pq.poll();

            if (end.equalsIgnoreCase(current.getOriginNode()) && current.getLatency() != 0) {
                return current.getLatency();
            }

            if (!visited.add(current.getOriginNode())) {
                continue;
            }

            for (String next : graph.getNeighbors(current.getOriginNode())) {
                int newLatency = current.getLatency() + graph.getLatency(current.getOriginNode(), next);
                pq.offer(new TraceLatencyState(next, null, newLatency));
            }

        }

        return -1;
    }

    public int countTraceUnderLatency(String data) {
        if (data == null) return -1;
        TraceLatencyState traceLatencyState = inputUtil.parseLatency(data);
        if (traceLatencyState == null) return -1;
        String start = traceLatencyState.getOriginNode();
        String end = traceLatencyState.getDestinationNode();
        int maxLatency = traceLatencyState.getLatency();

        if (!inputUtil.isValidNodeInput(start) || !inputUtil.isValidNodeInput(end) || maxLatency < 0) {
            return -1;
        }
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
