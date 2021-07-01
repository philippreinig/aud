package lectures.graphs;

import aud.util.DotViewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphTesting {

    private static List<Vertex<Integer>> createNVertices(final DirectedGraph<Integer> graph, final int n) {
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

        final DirectedGraph<Integer> graph = new DirectedGraph<>();
        final List<Vertex<Integer>> vertices = GraphTesting.createNVertices(graph, amountVertices);
        for (final var vertex : vertices) {
            graph.addNode(vertex);
        }

        for (int i = 0; i < 15; ++i) {
            graph.addEdge(new Edge(vertices.get(rand.nextInt(graph.getAmountVertices())), vertices.get(rand.nextInt(graph.getAmountVertices())), rand.nextInt(weightRange)));
        }
        System.out.println(graph);

        DotViewer.displayWindow(graph.toDot(), null);

    }


}
