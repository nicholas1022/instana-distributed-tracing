package instana;// main.util.Graph.java


// main.service.TraceSolver.java

// main.Main.java
import instana.util.Graph;
import instana.service.TraceService;
import instana.util.GraphFileInput;
import instana.util.InputUtil;

import java.io.*;
import java.util.Scanner;

public class Tracing {

    public static void main(String[] args) throws IOException {
        // Read input from file
        Graph graph = new Graph();
        GraphFileInput graphFileInput = new GraphFileInput();
        graphFileInput.inputGraph(graph);

        TraceService traceService = new TraceService(graph, new InputUtil());

        while (true) {
            System.out.println("Enter command (e.g.: trace-latency A-B-C)");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.exit(0);
            }

            traceService.runCommand(input);
        }

    }
}
