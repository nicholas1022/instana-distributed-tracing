package instana.service.inputData.graphInput;

import instana.core.GraphInterface;

public abstract class GraphInput implements GraphInputInterface {

    public void inputGraph(GraphInterface graph, String data) {
        String[] edges = data.split(",");
        for (String edge : edges) {
            graph.addEdge(edge.trim());
        }
    }
}
