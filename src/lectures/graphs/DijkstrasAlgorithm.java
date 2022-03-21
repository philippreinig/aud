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

    public static DijkstrasAlgorithmResult runDijkstrasAlgorithm(final DirectedGraph<?> graph, final Vertex<?> start) {
        if (DijkstrasAlgorithm.VERBOSE) {
            System.out.println("running Dijkstra's algorithm for " + graph + " with start node " + start);
        }
        final HashMap<Vertex<?>, Double> d = new HashMap<>();                       // d = (shortest) distance
        final HashMap<Vertex<?>, Vertex<?>> p = new HashMap<>();                    // p = predecessor node for shortest path
        for (final Vertex<?> v : graph.getVertices()) {
            d.put(v, Double.POSITIVE_INFINITY);
            p.put(v, null);
        }
        d.put(start, 0.0);
        final PriorityQueue<Vertex<?>> pq = new PriorityQueue<>((o1, o2) -> (int) (d.get(o1) - d.get(o2)));
        for (final Vertex<?> v : graph.getVertices()) {
            pq.push(v);
        }
        while (!pq.is_empty()) {
            if (DijkstrasAlgorithm.VERBOSE) {
                System.out.println(pq);
                System.out.println(d);
            }
            final Vertex<?> v = pq.pop();
            assert v != null;
            for (final Edge edge : v.getOutgoingEdges()) {
                final double new_d = d.get(v) + edge.getWeight();
                if (/**pq.contains(edge.getDestination())  && */new_d < d.get(edge.getDestination())) {
                    if (!pq.contains(edge.getDestination()))
                        System.err.println(edge.getDestination() + " not in pq anymore! (" + pq + ")");
                    d.put(edge.getDestination(), new_d);
                    p.put(edge.getDestination(), v);
                    pq.lower(edge.getDestination());
                }
            }
        }
        return new DijkstrasAlgorithmResult(d, p);
    }

    public static class DijkstrasAlgorithmResult {
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
