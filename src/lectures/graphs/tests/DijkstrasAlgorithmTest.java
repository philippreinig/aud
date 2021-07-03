package lectures.graphs.tests;

import aud.util.DotViewer;
import lectures.graphs.DijkstrasAlgorithm;
import lectures.graphs.DirectedGraph;
import lectures.graphs.util.GraphUtilities;
import util.Utilities;

import java.util.Arrays;

public class DijkstrasAlgorithmTest {

    public static void main(final String[] args) {
        final DirectedGraph<Integer> graph = GraphUtilities.createRandomGraph(10, 15, 100);
        System.out.println(Arrays.toString(graph.getEdgesArray()));
        DotViewer.displayWindow(graph.toDot(), null);

        final DijkstrasAlgorithm.DijkstrasAlgorithmResult dar = DijkstrasAlgorithm.runDijkstrasAlgorithm(graph, graph.getVertices().get(0));
        System.out.println("shortest paths: ");
        Utilities.printMap(dar.getShortestPathsPredecessorVertices());
        System.out.println("shortest distances: ");
        Utilities.printMap(dar.getShortestPathsDistances());

    }


}
