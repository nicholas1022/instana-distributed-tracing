package instana;

import instana.core.TraceGraph;
import instana.service.inputData.CommandLineInput;
import instana.service.inputData.FileReader;
import instana.service.inputData.commandInput.CommandCommandLineInput;
import instana.service.inputData.graphInput.GraphFileCommandLineInput;
import instana.service.inputData.graphInput.GraphFileInput;
import instana.service.trace.TraceService;

import java.io.IOException;
import java.util.Scanner;

public class Tracing {

    public static void main(String[] args) throws IOException {
        // Read graph data from file
        TraceGraph traceGraph = new TraceGraph();
        GraphFileInput graphFileInput = new GraphFileInput(new GraphFileCommandLineInput(), new FileReader(new Scanner(System.in)));
        graphFileInput.inputGraph(traceGraph);

        TraceService traceService = new TraceService(traceGraph);

        CommandLineInput dataInput = new CommandCommandLineInput();

//        read command from command line
        while (true) {
            System.out.println("Enter command (e.g.: trace-latency A-B-C)");

            String input = dataInput.inputData();
            if (input.equalsIgnoreCase("exit")) {
                System.exit(0);
            }

            traceService.runCommand(input);
        }

    }
}
