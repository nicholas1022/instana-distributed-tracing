package test;

import instana.model.TraceGraph;
import instana.service.trace.TraceService;
import instana.util.InputUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TraceServiceTest {
    private TraceService traceService;
    private TraceGraph traceGraph;

    @BeforeEach
    void setUp() {
        traceGraph = new TraceGraph();
        String[] edges = {"AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"};
        for (String edge : edges) {
            traceGraph.addEdge(edge);
        }
        traceService = new TraceService(traceGraph);
    }

    @Test
    void testTraceLatency() {
//        normal cases
        assertEquals("9", traceService.traceLatency("A-B-C")); // #1
        assertEquals("5", traceService.traceLatency("A-D")); // #2
        assertEquals("13", traceService.traceLatency("A-D-C")); // #3
        assertEquals("22", traceService.traceLatency("A-E-B-C-D")); // #4
        assertEquals("NO SUCH TRACE", traceService.traceLatency("A-E-D")); // #5

        assertEquals("18", traceService.traceLatency("A-D-C-E-B"));

////        extreme cases
////        traverse duplicate node
        assertEquals("30", traceService.traceLatency("A-B-C-D-E-B-C"));
        assertEquals("32", traceService.traceLatency("C-D-C-D-C"));

////        invalid input
        assertEquals("NO SUCH TRACE", traceService.traceLatency("?-!"));
        assertEquals("INVALID INPUT", traceService.traceLatency("A"));
        assertEquals("INVALID INPUT", traceService.traceLatency("AB"));
    }

    @Test
    void testMaxHopsTraces() {
//        normal cases
        assertEquals(2, traceService.countTraceUnderMaxHop("C-C3")); // #6
        assertEquals(4, traceService.countTraceUnderMaxHop("C-C4"));
        assertEquals(1, traceService.countTraceUnderMaxHop("A-B1"));
        assertEquals(35, traceService.countTraceUnderMaxHop("A-D9"));

        //        extreme cases
        assertEquals(0, traceService.countTraceUnderMaxHop("A-A1"));
        assertEquals(0, traceService.countTraceUnderMaxHop("A-B0"));

//        invalid input
        assertEquals(-1, traceService.countTraceUnderMaxHop("A-B-1"));
        assertEquals(-1, traceService.countTraceUnderMaxHop("1-25"));

    }

    @Test
    void testExactHopsTraces() {
        assertEquals(3, traceService.countTraceByExactHops("A-C4")); // #7

        assertEquals(1, traceService.countTraceByExactHops("A-D1"));
        assertEquals(0, traceService.countTraceByExactHops("A-D2"));
        assertEquals(2, traceService.countTraceByExactHops("A-D3"));
        assertEquals(1, traceService.countTraceByExactHops("A-D4"));
        assertEquals(3, traceService.countTraceByExactHops("A-D5"));
        assertEquals(3, traceService.countTraceByExactHops("A-D6"));
        assertEquals(6, traceService.countTraceByExactHops("A-D7"));
        assertEquals(7, traceService.countTraceByExactHops("A-D8"));
        assertEquals(12, traceService.countTraceByExactHops("A-D9"));
//
////        extreme cases
        assertEquals(0, traceService.countTraceByExactHops("A-A6"));
        assertEquals(0, traceService.countTraceByExactHops("C-A6"));
//
////        invalid input
        assertEquals(-1, traceService.countTraceByExactHops("A-B-1"));
        assertEquals(-1, traceService.countTraceByExactHops("1-25"));
        assertEquals(-1, traceService.countTraceByExactHops("-5"));
        assertEquals(-1, traceService.countTraceByExactHops("test-5"));
        assertEquals(-1, traceService.countTraceByExactHops(" -5"));
        assertEquals(-1, traceService.countTraceByExactHops("null-null-5"));
        assertEquals(-1, traceService.countTraceByExactHops(null));
    }

    @Test
    void testShortestPath() {
//        normal cases
        assertEquals(9, traceService.shortestLength("A-C")); // #8
        assertEquals(9, traceService.shortestLength("B-B")); // #9
        assertEquals(6, traceService.shortestLength("D-E"));
        assertEquals(5, traceService.shortestLength("C-B"));

//        extreme cases
        assertEquals(-1, traceService.shortestLength("D-A"));

        //        invalid input cases
        assertEquals(-1, traceService.shortestLength("1-3"));
        assertEquals(-1, traceService.shortestLength("1-?"));
        assertEquals(-1, traceService.shortestLength(" -"));
        assertEquals(-1, traceService.shortestLength("- "));
        assertEquals(-1, traceService.shortestLength(null));
    }

    @Test
    void testMaxLatencyTraces() {
//        normal cases
        assertEquals(7, traceService.countTraceUnderLatency("C-C30")); // #10
        assertEquals(1, traceService.countTraceUnderLatency("D-C9"));
        assertEquals(5, traceService.countTraceUnderLatency("D-C25"));

//        extreme cases
        assertEquals(0, traceService.countTraceUnderLatency("D-A25"));

//        invalid input
        assertEquals(-1, traceService.countTraceUnderLatency("C-C-1"));
        assertEquals(-1, traceService.countTraceUnderLatency("-25"));
        assertEquals(-1, traceService.countTraceUnderLatency("test-A25"));
        assertEquals(-1, traceService.countTraceUnderLatency("null-null-25"));
        assertEquals(-1, traceService.countTraceUnderLatency(null));

    }
}
