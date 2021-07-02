package lectures.graphs;

import aud.util.DotViewer;

import java.util.*;

public class GraphTesting {

    private static List<Vertex<Integer>> createNVertices(final Graph<Integer> graph, final int n) {
        final ArrayList<Vertex<Integer>> vertices = new ArrayList<>();
        for (int i = 1; i <= n; ++i) {
            vertices.add(new Vertex<>(graph, i));
        }
        return vertices;
    }

    private static void printAdjacencyMatrix(final List<List<Integer>> adjacencyMatrix) {
        for (int i = 0; i < adjacencyMatrix.size(); ++i) {
            System.out.print("[");
            if (!adjacencyMatrix.isEmpty()) {
                for (int j = 0; j < adjacencyMatrix.size() - 1; ++j) {
                    System.out.print(adjacencyMatrix.get(i).get(j).toString() + ", ");
                }
            }
            System.out.print(adjacencyMatrix.get(i).get(adjacencyMatrix.size() - 1).toString());
            System.out.println("]");
        }
    }

    private static void printMap(final Map<?, ?> map) {
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(final String[] args) {
//        DirectedGraph graph = new DirectedGraph();
//        List<Vertex<Integer>> vertices = createNVertices(10);
//        for (Vertex<Integer> vertex : vertices) graph.addNode(vertex);
//        System.out.println(graph);
//        graph.addEdge(vertices.get(0), vertices.get(1), 5);
//        graph.addEdge(vertices.get(0), vertices.get(2), 3);
//        graph.addEdge(vertices.get(2), vertices.get(3), 7);
//        graph.addEdge(vertices.get(7), vertices.get(4), 2);
//        graph.addEdge(vertices.get(1), vertices.get(9), 1);
//        System.out.println(graph);
//        printAdjacencyMatrix(graph.getAdjacencyMatrix());

        final int amountVertices = 10;
        final int weightRange = 100;
        final Random rand = new Random();

        final Graph<Integer> graph = new Graph<>(false);
        final List<Vertex<Integer>> vertices = GraphTesting.createNVertices(graph, amountVertices);
        for (final var vertex : vertices) {
            graph.addNode(vertex);
        }

        for (int i = 0; i < 15; ++i) {
            graph.addEdge(new Edge(vertices.get(rand.nextInt(graph.getAmountVertices())), vertices.get(rand.nextInt(graph.getAmountVertices())), rand.nextInt(weightRange)));
        }
        System.out.println(Arrays.toString(graph.getEdgesArray()));

        DotViewer.displayWindow(graph.toDot(), null);

        final DijkstrasAlgorithm.DijkstrasAlgorithmResult dar = DijkstrasAlgorithm.runDijkstrasAlgorithm(graph, vertices.get(0));
        System.out.println("shortest paths: ");
        GraphTesting.printMap(dar.getShortestPathsPredecessorVertices());
        System.out.println("shortest distances: ");
        GraphTesting.printMap(dar.getShortestPathsDistances());

    }


}
