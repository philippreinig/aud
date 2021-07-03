package lectures.graphs.util;

import lectures.graphs.DirectedGraph;
import lectures.graphs.Edge;
import lectures.graphs.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphUtilities {
    private static final Random rand = new Random();

    private GraphUtilities() {
    }

    public static List<Vertex<Integer>> createNVertices(final DirectedGraph<Integer> graph, final int n) {
        final ArrayList<Vertex<Integer>> vertices = new ArrayList<>();
        for (int i = 1; i <= n; ++i) {
            vertices.add(new Vertex<>(graph, i));
        }
        return vertices;
    }

    public static DirectedGraph<Integer> createRandomGraph(final int amountVertices, final int amountEdges, final int edgeWeightRange) {
        final DirectedGraph<Integer> graph = new DirectedGraph<>();
        final List<Vertex<Integer>> vertices = GraphUtilities.createNVertices(graph, amountVertices);
        for (final var vertex : vertices) {
            graph.addNode(vertex);
        }
        for (int i = 0; i < amountEdges; ++i) {
            graph.addEdge(new Edge(vertices.get(GraphUtilities.rand.nextInt(graph.getAmountVertices())), vertices.get(GraphUtilities.rand.nextInt(graph.getAmountVertices())), GraphUtilities.rand.nextInt(edgeWeightRange)));
        }
        return graph;
    }

}
