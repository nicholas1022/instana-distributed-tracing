package test;

import instana.core.TraceGraph;
import instana.service.inputData.FileReader;
import instana.service.inputData.graphInput.GraphFileCommandLineInput;
import instana.service.inputData.graphInput.GraphFileInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


class TraceGraphFileInputTest {
    private GraphFileInput graphFileInput;


    @BeforeEach
    void setUp() {
        graphFileInput = new GraphFileInput(new GraphFileCommandLineInput(), new FileReader(new Scanner(System.in)));
    }

    @Test
    void testGraphInput() {

        TraceGraph expectedTraceGraph = new TraceGraph();
        String[] edges = {"AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"};
        for (String edge : edges) {
            expectedTraceGraph.addEdge(edge);
        }

        TraceGraph traceGraphSample = new TraceGraph();
        graphFileInput.inputGraph(traceGraphSample, "input.txt");

//        normal cases
        assertEquals(expectedTraceGraph, traceGraphSample);
    }
}
