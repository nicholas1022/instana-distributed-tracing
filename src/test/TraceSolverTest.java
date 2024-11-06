package test;

import main.util.Graph;
import main.service.TraceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TraceSolverTest {
    private TraceService solver;
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph();
        String[] edges = {"AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"};
        for (String edge : edges) {
            graph.addEdge(edge);
        }
        solver = new TraceService(graph);
    }

//    @Test
    void testTraceLatency() {
//        normal cases
        assertEquals("9", solver.traceLatency("A-B-C")); // #1
        assertEquals("5", solver.traceLatency("A-D")); // #2
        assertEquals("13", solver.traceLatency("A-D-C")); // #3
        assertEquals("22", solver.traceLatency("A-E-B-C-D")); // #4
        assertEquals("NO SUCH TRACE", solver.traceLatency("A-E-D")); // #5

        assertEquals("18", solver.traceLatency("A-D-C-E-B"));

//        extreme cases
//        traverse duplicate node
        assertEquals("30", solver.traceLatency("A-B-C-D-E-B-C"));
    }

    @Test
    void testMaxHopsTraces() {
//        normal cases
        assertEquals(2, solver.countTraceUnderMaxHop("C", "C", 3)); // #6
        assertEquals(4, solver.countTraceUnderMaxHop("C", "C", 4));
//        extreme cases
//        text extremely large max hops

//        assertEquals(66, solver.countMaxHopsTraces("A", "D", 99));
        assertEquals(35, solver.countTraceUnderMaxHop("A", "D", 9));
    }

    @Test
    void testExactHopsTraces() {
        assertEquals(3, solver.countTraceByExactHops("A", "C", 4)); // #7

        assertEquals(1, solver.countTraceByExactHops("A", "D", 1));
        assertEquals(0, solver.countTraceByExactHops("A", "D", 2));
        assertEquals(2, solver.countTraceByExactHops("A", "D", 3));
        assertEquals(1, solver.countTraceByExactHops("A", "D", 4));
        assertEquals(3, solver.countTraceByExactHops("A", "D", 5));
        assertEquals(3, solver.countTraceByExactHops("A", "D", 6));
        assertEquals(6, solver.countTraceByExactHops("A", "D", 7));
        assertEquals(7, solver.countTraceByExactHops("A", "D", 8));
        assertEquals(12, solver.countTraceByExactHops("A", "D", 9));

//        extreme cases
        assertEquals(0, solver.countTraceByExactHops("A", "A", 6));
        assertEquals(0, solver.countTraceByExactHops("C", "A", 6));

    }

    @Test
    void testShortestPath() {
//        normal cases
        assertEquals(9, solver.shortestLength("A", "C")); // #8
        assertEquals(9, solver.shortestLength("B", "B")); // #9
        assertEquals(6, solver.shortestLength("D", "E"));
        assertEquals(5, solver.shortestLength("C", "B"));
//        invalid input cases
        assertEquals(-1, solver.shortestLength("D", "A"));
    }

    @Test
    void testMaxLatencyTraces() {
//        normal cases
        assertEquals(7, solver.countTraceUnderLatency("C", "C", 30)); // #10
        assertEquals(1, solver.countTraceUnderLatency("D", "C", 9));
        assertEquals(5, solver.countTraceUnderLatency("D", "C", 25));

//        invalid input cases
        assertEquals(0, solver.countTraceUnderLatency("D", "A", 25));

    }
}
