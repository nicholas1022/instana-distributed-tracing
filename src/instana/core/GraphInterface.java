package instana.core;

import java.util.Set;

public interface GraphInterface {

    void addEdge(String edge);

    Set<String> getNeighbors(String node);
}
