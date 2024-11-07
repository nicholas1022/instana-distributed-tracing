package instana.service;

public interface CountTraceByLatencyInterface {
    public int countTraceWithExactHops(String start, String end, int exactHops);
}
