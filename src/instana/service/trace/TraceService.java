package instana.service.trace;

import instana.model.TraceLatencyState;
import instana.core.TraceGraph;
import instana.service.inputData.FileReader;
import instana.model.TraceHopState;
import instana.util.InputUtil;

import java.util.*;

public class TraceService {
    private TraceGraph traceGraph;

    public TraceService(TraceGraph traceGraph) {
        this.traceGraph = traceGraph;
    }

    
    public String traceLatency(String trace) {
        int totalLatency = 0;

        String[] services = trace.split("-");
        if(services.length < 2) {
            return "INVALID INPUT";
        }
        for (int i = 0; i < services.length - 1; i++) {
            String start = services[i];
            String end = services[i + 1];
            Integer weight = traceGraph.getLatency(start, end);
            if (weight == null) {
                return "NO SUCH TRACE";
            }
            totalLatency += weight;
        }
        return String.valueOf(totalLatency);
    }

    
    public int countTraceUnderMaxHop(String data) {

        TraceHopState traceHopState = InputUtil.parseHop(data);

        if (traceHopState == null) return -1;

        String start = traceHopState.getOriginNode();
        String end = traceHopState.getDestinationNode();
        int maxHops = traceHopState.getHop();

        if (maxHops < 0) {
            return -1;
        }

        if (!InputUtil.isValidNodeInput(start) || !InputUtil.isValidNodeInput(end)) {
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

        for (String next : traceGraph.getNeighbors(current)) {
            count += countTraceUnderMaxHop(next, end, maxHops, currentHops + 1);
        }
        return count;
    }

    
    public int countTraceByExactHops(String data) {

        TraceHopState traceHopeState = InputUtil.parseHop(data);
        if (traceHopeState == null) return -1;

        String start = traceHopeState.getOriginNode();
        String end = traceHopeState.getDestinationNode();
        int exactHops = traceHopeState.getHop();

        if (exactHops < 0) {
            return -1;
        }

        if (!InputUtil.isValidNodeInput(start) || !InputUtil.isValidNodeInput(end)) {
            return -1;
        }

        return countTraceByExactHopsHelper(start, end, exactHops);
    }

    public int countTraceByExactHopsHelper(String start, String end, int exactHops) {

        if (exactHops == 0) {
            return start.equalsIgnoreCase(end) ? 1 : 0;
        }

        int count = 0;
        for (String next : traceGraph.getNeighbors(start)) {
            count += countTraceByExactHopsHelper(next, end, exactHops - 1);
        }
        return count;
    }

    
    public int shortestLength(String trace) {
        if (trace == null) return -1;

        String[] services = trace.split("-");
        if (services.length != 2) {
            return -1;
        }
        String start = services[0];
        String end = services[1];
        if (trace.length() <= 1 || !InputUtil.isValidNodeInput(start) || !InputUtil.isValidNodeInput(end)) {
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

            for (String next : traceGraph.getNeighbors(current.getOriginNode())) {
                int newLatency = current.getLatency() + traceGraph.getLatency(current.getOriginNode(), next);
                pq.offer(new TraceLatencyState(next, null, newLatency));
            }

        }

        return -1;
    }

    public int countTraceUnderLatency(String data) {
        if (data == null) return -1;
        TraceLatencyState traceLatencyState = InputUtil.parseLatency(data);
        if (traceLatencyState == null) return -1;
        String start = traceLatencyState.getOriginNode();
        String end = traceLatencyState.getDestinationNode();
        int maxLatency = traceLatencyState.getLatency();

        if (!InputUtil.isValidNodeInput(start) || !InputUtil.isValidNodeInput(end) || maxLatency < 0) {
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

        for (String next : traceGraph.getNeighbors(current)) {
            int newLatency = currentLatency + traceGraph.getLatency(current, next);
            count += countTraceUnderLatency(next, end, maxLatency, newLatency, true);
        }

        return count;
    }

    public void batchJob(String file) {
        FileReader fileReader = new FileReader(new Scanner(System.in));
        String inputLine = fileReader.readData(file);
        String[] inputArr = inputLine.split(",");
        for (String commandInput : inputArr) {
            runCommand(commandInput);
        }
    }

    public void runCommand(String commandParam) {
        String[] inputArr = commandParam.trim().split(" ");
        if (inputArr.length < 2) {
            System.out.println("INVALID INPUT");
            return;
        }
        String command = inputArr[0].trim();
        String data = inputArr[1].trim();

        switch (command) {
            case "trace-latency":
                System.out.println(traceLatency(data));
                break;
            case "count-trace-with-max-hops":
                System.out.println(countTraceUnderMaxHop(data));
                break;
            case "count-trace-with-exact-hops":
                System.out.println(countTraceByExactHops(data));
                break;
            case "shortest-length":
                System.out.println(shortestLength(data));
                break;
            case "count-trace-with-max-latency":
                System.out.println(countTraceUnderLatency(data));
                break;
            case "batch-job":
                batchJob(data);
                break;
        }
    }
}
