package main.model;

public class TraceHopState extends AbstractHopState {
    private int hop;

    public TraceHopState(String node, int latency, int hop) {
        super.setNode(node);
        super.setLatency(latency);
        this.hop = hop;
    }


    public int getHop() {
        return hop;
    }

    public void setHop(int hop) {
        this.hop = hop;
    }
}