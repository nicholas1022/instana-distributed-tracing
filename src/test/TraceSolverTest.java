package test;

import main.Graph;
import main.TraceSolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TraceSolverTest {
    private TraceSolver solver;
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph();
        String[] edges = {"AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"};
        for (String edge : edges) {
            graph.addEdge(edge);
        }
        solver = new TraceSolver(graph);
    }

    @Test
    void testFixedTraces() {
        assertEquals("9", solver.calculateTraceLatency("A-B-C"));
        assertEquals("5", solver.calculateTraceLatency("A-D"));
        assertEquals("13", solver.calculateTraceLatency("A-D-C"));
        assertEquals("22", solver.calculateTraceLatency("A-E-B-C-D"));
        assertEquals("NO SUCH TRACE", solver.calculateTraceLatency("A-E-D"));
    }

    @Test
    void testMaxHopsTraces() {
        assertEquals(2, solver.countTracesWithMaxHops("C", "C", 3));
    }

    @Test
    void testExactHopsTraces() {
        assertEquals(3, solver.countTracesWithExactHops("A", "C", 4));
    }

    @Test
    void testShortestPath() {
        assertEquals(9, solver.shortestLength("A", "C"));
        assertEquals(7, solver.shortestLength("A", "E"));
        assertEquals(15, solver.shortestLength("E", "D"));
        assertEquals(9, solver.shortestLength("B", "B"));
        assertEquals(5, solver.shortestLength("C", "B"));
    }

    @Test
    void testTracesUnderLatency() {
        assertEquals(7, solver.countTracesWithMaxLatency("C", "C", 30));
    }
}
