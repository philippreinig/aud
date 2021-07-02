package lectures.graphs;

import aud.PriorityQueue;

import java.util.HashMap;
import java.util.Map;

public class DijkstrasAlgorithm {
    private static final boolean VERBOSE = true;

    /**
     * Private constructor to hide implicit public one
     */
    private DijkstrasAlgorithm() {
    }

    public static DijkstrasAlgorithmResult runDijkstrasAlgorithm(final Graph<?> graph, final Vertex<?> startNode) {
        if (DijkstrasAlgorithm.VERBOSE) {
            System.out.println("running Dijkstra's algorithm for " + graph + " with start node " + startNode);
        }
        final HashMap<Vertex<?>, Double> shortestPathsDistances = new HashMap<>();
        final HashMap<Vertex<?>, Vertex<?>> shortestPathsPredecessorVertices = new HashMap<>();
        for (final Vertex<?> vertex : graph.getVertices()) {
            shortestPathsDistances.put(vertex, Double.POSITIVE_INFINITY);
            shortestPathsPredecessorVertices.put(vertex, null);
        }
        shortestPathsDistances.put(startNode, 0.0);
        final PriorityQueue<Vertex<?>> priorityQueue = new PriorityQueue<>();
        for (final Vertex<?> vertex : graph.getVertices()) {
            priorityQueue.push(vertex);
        }
        while (!priorityQueue.is_empty()) {
            final Vertex<?> vertex = priorityQueue.pop();
            for (final Edge edge : vertex.getOutgoingEdges()) {
                final double check_distance = shortestPathsDistances.get(vertex) + edge.getWeight();
                if (check_distance < shortestPathsDistances.get(edge.getDestination())) {
                    shortestPathsDistances.put(edge.getDestination(), check_distance);
                    shortestPathsPredecessorVertices.put(edge.getDestination(), vertex);
                }
            }
        }
        return new DijkstrasAlgorithmResult(shortestPathsDistances, shortestPathsPredecessorVertices);
    }

    static class DijkstrasAlgorithmResult {
        private final HashMap<Vertex<?>, Vertex<?>> shortestPathsPredecessorVertices;
        private final HashMap<Vertex<?>, Double> shortestPathsDistances;

        DijkstrasAlgorithmResult(final HashMap<Vertex<?>, Double> shortestPathsDistances, final HashMap<Vertex<?>, Vertex<?>> shortestPathsPredecessorVertices) {
            this.shortestPathsDistances = shortestPathsDistances;
            this.shortestPathsPredecessorVertices = shortestPathsPredecessorVertices;
        }

        public Map<Vertex<?>, Vertex<?>> getShortestPathsPredecessorVertices() {
            return this.shortestPathsPredecessorVertices;
        }

        public Map<Vertex<?>, Double> getShortestPathsDistances() {
            return this.shortestPathsDistances;
        }

    }
}
