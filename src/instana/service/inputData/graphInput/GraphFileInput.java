package instana.service.inputData.graphInput;

import instana.model.TraceGraph;
import instana.service.graph.GraphInput;
import instana.service.inputData.FileReader;
import instana.service.inputData.InputDataInterface;

//read the graph data from file
public class GraphFileInput extends GraphInput {


    InputDataInterface graphFileCommandLineInput;
    FileReader fileReader;

    public GraphFileInput(InputDataInterface graphFileCommandLineInput, FileReader fileReader) {
        this.graphFileCommandLineInput = graphFileCommandLineInput;
        this.fileReader = fileReader;
    }

// input data to graph
    public void inputGraph(TraceGraph traceGraph) {
        // read file path from command line
        String fileName = graphFileCommandLineInput.inputData();
        this.inputGraph(traceGraph, fileName);
    }

    public void inputGraph(TraceGraph traceGraph, String fileName) {
//        read data from the file
        String data = fileReader.readData(fileName);
        super.inputGraph(traceGraph, data);
    }
}
