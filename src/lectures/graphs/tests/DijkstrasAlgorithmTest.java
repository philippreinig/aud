package lectures.graphs.tests;

import aud.util.DotViewer;
import lectures.graphs.DijkstrasAlgorithm;
import lectures.graphs.DirectedGraph;
import lectures.graphs.Edge;
import lectures.graphs.Vertex;
import util.Utilities;

public class DijkstrasAlgorithmTest {

    public static void main(final String[] args) {
//        final DirectedGraph<Integer> graph = GraphUtilities.createRandomGraph(10, 15, 100);
//        System.out.println(Arrays.toString(graph.getEdgesArray()));
//        DotViewer.displayWindow(graph.toDot(), null);
//
//        final DijkstrasAlgorithm.DijkstrasAlgorithmResult dar = DijkstrasAlgorithm.runDijkstrasAlgorithm(graph, graph.getVertices().get(0));
//        System.out.println("shortest paths: ");
//        Utilities.printMap(dar.getShortestPathsPredecessorVertices());
//        System.out.println("shortest distances: ");
//        Utilities.printMap(dar.getShortestPathsDistances());

        final DirectedGraph<String> graph = new DirectedGraph<>();
        final Vertex<String> berlin = new Vertex<>(graph, "Berlin");
        final Vertex<String> detroit = new Vertex<>(graph, "Detroit");
        final Vertex<String> chicago = new Vertex<>(graph, "Chicago");
        final Vertex<String> lissabon = new Vertex<>(graph, "Lissabon");
        final Vertex<String> frankfurt = new Vertex<>(graph, "Frankfurt");
        final Vertex<String> paris = new Vertex<>(graph, "Paris");
        final Vertex<String> toronto = new Vertex<>(graph, "Toronto");
        final Vertex<String> newyork = new Vertex<>(graph, "New York");

        graph.addNode(berlin);
        graph.addNode(detroit);
        graph.addNode(chicago);
        graph.addNode(lissabon);
        graph.addNode(frankfurt);
        graph.addNode(paris);
        graph.addNode(toronto);
        graph.addNode(newyork);


        final Edge e1 = new Edge(berlin, frankfurt, 1);
        final Edge e2 = new Edge(berlin, paris, 2);
        final Edge e3 = new Edge(berlin, lissabon, 3);
        final Edge e4 = new Edge(frankfurt, newyork, 6);
        final Edge e5 = new Edge(frankfurt, toronto, 5);
        final Edge e6 = new Edge(paris, newyork, 5);
        final Edge e7 = new Edge(paris, toronto, 6);
        final Edge e8 = new Edge(lissabon, toronto, 4);
        final Edge e9 = new Edge(lissabon, chicago, 5);
        final Edge e10 = new Edge(newyork, detroit, 3);
        final Edge e11 = new Edge(toronto, detroit, 3);
        final Edge e12 = new Edge(chicago, detroit, 4);

        graph.addEdge(e1);
        graph.addEdge(e2);
        graph.addEdge(e3);
        graph.addEdge(e4);
        graph.addEdge(e5);
        graph.addEdge(e6);
        graph.addEdge(e7);
        graph.addEdge(e8);
        graph.addEdge(e9);
        graph.addEdge(e10);
        graph.addEdge(e11);
        graph.addEdge(e12);

        DotViewer.displayWindow(graph.toDot(), null);

        final DijkstrasAlgorithm.DijkstrasAlgorithmResult result = DijkstrasAlgorithm.runDijkstrasAlgorithm(graph, berlin);
        System.out.println("shortest paths: ");
        Utilities.printMap(result.getShortestPathsPredecessorVertices());
        System.out.println("shortest distances: ");
        Utilities.printMap(result.getShortestPathsDistances());


    }


}
