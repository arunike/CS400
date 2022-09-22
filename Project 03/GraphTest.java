// --== CS400 File Header Information ==--
// Name: <Richie Zhou>
// Email: <zhou469@wisc.edu>
// Team: <AG>
// TA: <Karan>
// Lecturer: <Gary Dahl>

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.platform.console.ConsoleLauncher;
import java.lang.invoke.MethodHandles;

/**
 * Tests the implementation of CS400Graph for the individual component of
 * Project Three: the implementation of Dijkstra's Shortest Path algorithm.
 */
public class GraphTest {

    private CS400Graph<String> graph;

    /**
     * Instantiate graph from last week's shortest path activity.
     */
    @BeforeEach
    public void createGraph() {
        graph = new CS400Graph<>();

        // insert vertices A-F
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("F");

        // insert edges from Week 11. Shortest Path Activity
        graph.insertEdge("A","B",6);
        graph.insertEdge("A","C",2);
        graph.insertEdge("A","D",5);
        graph.insertEdge("B","E",1);
        graph.insertEdge("B","C",2);
        graph.insertEdge("C","B",3);
        graph.insertEdge("C","F",1);
        graph.insertEdge("D","E",3);
        graph.insertEdge("E","A",4);
        graph.insertEdge("F","A",1);
        graph.insertEdge("F","D",1);
    }

    /**
     * Checks the distance/total weight cost from the vertex A to F.
     */
    @Test
    public void testPathCostAtoF() {
        assertTrue(graph.getPathCost("A", "F") == 3);
    }

    /**
     * Checks the distance/total weight cost from the vertex A to D.
     */
    @Test
    public void testPathCostAtoD() {
        assertTrue(graph.getPathCost("A", "D") == 4);
    }

    /**
     * Checks the ordered sequence of data within vertices from the vertex
     * A to D.
     */
    @Test
    public void testPathAtoD() {
        assertTrue(graph.shortestPath("A", "D").toString().equals(
                "[A, C, F, D]"
        ));
    }

    /**
     * Checks the ordered sequence of data within vertices from the vertex
     * A to F.
     */
    @Test
    public void testPathAtoF() {
        assertTrue(graph.shortestPath("A", "F").toString().equals(
                "[A, C, F]"
        ));
    }

    @Test // Add an extra test method to confirm that the distance you computed for this node in last week's activity is correct.
    public void testDistance() {
        assertTrue(graph.getPathCost("E", "D") == 8);
    }

    @Test // Add an extra test method to confirm the sequence of nodes along the path from your source node to this same end node (the end node that is furthest from your source node) is correct.
    public void testSequence() {
        String[] path = graph.shortestPath("F", "B").toString().split(", "); // Split path by comma
        String predecessor = path[path.length - 2].trim(); // Decrease path length
        assertEquals("C", predecessor);
    }

    @Test // Add another test method to confirm the path cost you reported for question 2 of last week's activity.
    public void testPathCost() {
        assertTrue(graph.getPathCost("E", "F") == 7);
    }

    @Test // Add another test method to confirm the predecessor you reported for question 3 of last week's activity.
    public void testPredecessor() {
        assertTrue(graph.shortestPath("F", "B").toString().equals("[F, A, C, B]"));
    }

    @Test // Add at least one more test method to this class to help convince yourself of the correctness of your implementation.
    public void testConvince() {
        assertTrue(graph.shortestPath("E", "F").toString().equals("[E, A, C, F]"));
        assertTrue(graph.getPathCost("E", "B") == 9);
    }

    public static void main(String[] args) {
        String className = MethodHandles.lookup().lookupClass().getName();
        String classPath = System.getProperty("java.class.path").replace(" ", "\\ ");
        String[] arguments = new String[] {
                "-cp",
                classPath,
                "--include-classname=.*",
                "--select-class=" + className };
        ConsoleLauncher.main(arguments);
    }
}