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

//            String[] inputArr = input.split(" ");
//            String command = inputArr[0].trim();
//            String data = inputArr[1].trim();
//            switch (command) {
//                case "trace-latency":
//                    System.out.println(traceService.traceLatency(data));
//                    break;
//                case "count-trace-with-max-hops":
//                    System.out.println(traceService.countTraceUnderMaxHop(data));
//                    break;
//                case "count-trace-with-exact-hops":
//                    System.out.println(traceService.countTraceByExactHops(data));
//                    break;
//                case "shortest-length":
//                    System.out.println(traceService.shortestLength(data));
//                    break;
//                case "count-trace-with-max-latency":
//                    System.out.println(traceService.countTraceUnderLatency(data));
//                    break;
//            }
        }

    }
}
