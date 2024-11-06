package main;

class TraceIterationState {
    private String node;
    private int latency;
    private int hop;

    TraceIterationState(String node, int latency, int hop) {
        this.node = node;
        this.latency = latency;
        this.hop = hop;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public int getHop() {
        return hop;
    }

    public void setHop(int hop) {
        this.hop = hop;
    }
}