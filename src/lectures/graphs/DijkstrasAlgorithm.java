package lectures.graphs;

import java.util.HashMap;

public class DijkstrasAlgorithm {

    public static DijkstrasAlgorithmResult runDijkstrasAlgorithm(final DirectedGraph graph, final Vertex<?> startNode) {
        return new DijkstrasAlgorithmResult(null, null);
    }

    private static class DijkstrasAlgorithmResult {
        private final HashMap<Vertex<?>, Vertex<?>> shortestConnections;
        private final HashMap<Vertex<?>, Double> shortestPaths;

        DijkstrasAlgorithmResult(final HashMap<Vertex<?>, Vertex<?>> shortestConnections, final HashMap<Vertex<?>, Double> shortestPaths) {
            this.shortestConnections = shortestConnections;
            this.shortestPaths = shortestPaths;
        }

        public HashMap<Vertex<?>, Vertex<?>> getShortestConnections() {
            return this.shortestConnections;
        }

        public HashMap<Vertex<?>, Double> getShortestPaths() {
            return this.shortestPaths;
        }

    }
}
