package lectures.graphs.tests;

import aud.util.DotViewer;
import lectures.graphs.DirectedGraph;
import lectures.graphs.util.GraphUtilities;
import util.Utilities;

public class GraphDatastructureTest {
    public static void main(final String[] args) {
        final DirectedGraph<Integer> graph = GraphUtilities.createRandomGraph(10, 15, 100);
        System.out.println(graph);
        Utilities.print2DArray(graph.getAdjacencyMatrix());
        DotViewer.displayWindow(graph.toDot(), null);
    }
}
