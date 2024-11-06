package main.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class GraphFileInput implements GraphInputInterface{

    Scanner sc;

    public GraphFileInput() {
        this.sc = new Scanner(System.in);
    }

    public void inputGraph(Graph graph) {
        System.out.println("Enter input file name");
        String inputFileName = sc.nextLine();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(inputFileName))) {
            String line = reader.readLine();
            String[] edges = line.split(",");
            for (String edge : edges) {
                graph.addEdge(edge.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
