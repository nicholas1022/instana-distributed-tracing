package instana.model;

public class TraceHopState extends AbstractState {
    private int hop;

    public TraceHopState(String origin, String destination, int hop) {
        super.setOriginNode(origin);
        super.setDestinationNode(destination);
        this.hop = hop;
    }

    public int getHop() {
        return hop;
    }

    public void setHop(int hop) {
        this.hop = hop;
    }
}