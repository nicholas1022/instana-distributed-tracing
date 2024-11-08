package instana;

import instana.core.TraceGraph;
import instana.service.inputData.CLIInput;
import instana.service.inputData.FileReader;
import instana.service.inputData.commandInput.CommandCLIInputService;
import instana.service.inputData.graphInput.GraphFileCLIInputService;
import instana.service.inputData.graphInput.GraphFileInputService;
import instana.service.trace.TraceService;

import java.io.IOException;
import java.util.Scanner;

public class Tracing {

    public static void main(String[] args) throws IOException {
        // Read graph data from file
        TraceGraph traceGraph = new TraceGraph();
        GraphFileInputService graphFileInputService = new GraphFileInputService(new GraphFileCLIInputService(), new FileReader(new Scanner(System.in)));
        graphFileInputService.inputGraph(traceGraph);

        TraceService traceService = new TraceService(traceGraph);

        CLIInput dataInput = new CommandCLIInputService();

//        read command from command line
        while (true) {
            String input = dataInput.inputData();
            traceService.runCommand(input);
        }

    }
}
