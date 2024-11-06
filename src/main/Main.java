package main;// main.Graph.java


// main.TraceSolver.java

// main.Main.java
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // Read input from file
        Graph graph = new Graph();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter input file name");
        String inputFileName = sc.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line = reader.readLine();
            String[] edges = line.split(",");
            for (String edge : edges) {
                graph.addEdge(edge.trim());
            }
        }

        TraceSolver solver = new TraceSolver(graph);

        while (true) {
            System.out.println("Enter command");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) System.exit(0);
            String[] inputArr = input.split(" ");
            String command = inputArr[0].trim();
            String data = inputArr[1].trim();
            String start = data.substring(0, 1);
            String end = data.substring(1, 2);
            switch (command) {
                case "trace-latency":
                    System.out.println(solver.calculateTraceLatency(data));
                    break;
                case "count-trace-with-max-hops":
                    int maxHop = Integer.parseInt(data.substring(2));
                    System.out.println(solver.countTracesWithMaxHops(start, end, maxHop));
                    break;
                case "count-trace-with-exact-hops":
                    int hop = Integer.parseInt(data.substring(2));
                    System.out.println(solver.countTracesWithExactHops(start, end, hop));
                    break;
                case "shortest-length":
                    System.out.println(solver.shortestLength(start, end));
                    break;
                case "count-trace-with-max-latency":
                    int maxLatency = Integer.parseInt(data.substring(2));
                    System.out.println(solver.countTracesWithMaxLatency(start, end, maxLatency));
                    break;
            }
        }
        // Calculate and print all solutions
//        System.out.println(solver.calculateTraceLatency("A-B-C"));  // 1
//        System.out.println(solver.calculateTraceLatency("A-D"));   // 2
//        System.out.println(solver.calculateTraceLatency("A-D-C"));  // 3
//        System.out.println(solver.calculateTraceLatency("A-E-B-C-D")); // 4
//        System.out.println(solver.calculateTraceLatency("A-E-D"));  // 5
//        System.out.println(solver.countTracesWithMaxHops("C", "C", 3)); // 6
//        System.out.println(solver.countTracesWithExactHops("A", "C", 4)); // 7
//        System.out.println(solver.shortestLength("A", "C")); // 8
//        System.out.println(solver.shortestLength("B", "B")); // 9
//        System.out.println(solver.countTracesWithMaxLatency("C", "C", 30)); // 10
    }
}
