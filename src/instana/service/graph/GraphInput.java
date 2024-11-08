package instana.service.graph;

import instana.model.GraphInterface;
import instana.service.inputData.graphInput.GraphInputInterface;

public abstract class GraphInput implements GraphInputInterface {

    public void inputGraph(GraphInterface graph, String data) {
        String[] edges = data.split(",");
        for (String edge : edges) {
            graph.addEdge(edge.trim());
        }
    }
}
