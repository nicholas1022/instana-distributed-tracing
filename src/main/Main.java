package main;// main.util.Graph.java


// main.service.TraceSolver.java

// main.Main.java
import main.util.Graph;
import main.service.TraceService;
import main.util.GraphFileInput;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        // Read input from file
        Graph graph = new Graph();
        GraphFileInput graphFileInput = new GraphFileInput();
        graphFileInput.inputGraph(graph);

        TraceService solver = new TraceService(graph);

        while (true) {
            System.out.println("Enter command (e.g.: trace-latency A-B-C)");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.exit(0);
            }

            String[] inputArr = input.split(" ");
            String command = inputArr[0].trim();
            String data = inputArr[1].trim();
            String start = data.substring(0, 1);
            String end = data.substring(1, 2);
            switch (command) {
                case "trace-latency":
                    System.out.println(solver.traceLatency(data));
                    break;
                case "count-trace-with-max-hops":
                    int maxHop = Integer.parseInt(data.substring(2));
                    System.out.println(solver.countTraceUnderMaxHop(start, end, maxHop));
                    break;
                case "count-trace-with-exact-hops":
                    int hop = Integer.parseInt(data.substring(2));
                    System.out.println(solver.countTraceByExactHops(start, end, hop));
                    break;
                case "shortest-length":
                    System.out.println(solver.shortestLength(start, end));
                    break;
                case "count-trace-with-max-latency":
                    int maxLatency = Integer.parseInt(data.substring(2));
                    System.out.println(solver.countTraceUnderLatency(start, end, maxLatency));
                    break;
            }
        }

    }
}
