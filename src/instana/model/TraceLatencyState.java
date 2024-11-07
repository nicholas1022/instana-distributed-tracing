package instana.model;

public class TraceLatencyState extends AbstractState {

    private int latency;

    public TraceLatencyState(String origin, String destination, int latency) {
        super.setOriginNode(origin);
        super.setDestinationNode(destination);
        this.latency = latency;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }
}