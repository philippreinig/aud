package lectures.graphs;

import aud.util.GraphvizDecorable;
import aud.util.GraphvizDecorator;
import aud.util.Graphvizable;

import java.util.ArrayList;

public class DirectedGraph<T> implements Graphvizable, GraphvizDecorable {
    private final ArrayList<Vertex<T>> vertices;
    private final ArrayList<Edge> edges;

    private int nextVertexIndex = 0;

    DirectedGraph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
//        this(new ArrayList<>(), new ArrayList<>());
    }

//    DirectedGraph(final ArrayList<Vertex<T>> nodes, final ArrayList<Edge> edges) {
//        this.vertices = nodes;
//        this.edges = edges;
//    }

    public boolean addNode(final Vertex<T> node) {
        if (this.vertices.contains(node)) {
            return false;
        } else {
            this.vertices.add(node);
            return true;
        }
    }

    public boolean removeNode(final Vertex<T> node) {
        if (this.vertices.contains(node)) {
            this.vertices.remove(node);
            return true;
        } else {
            return false;
        }
    }

    public boolean addEdge(final Edge edge) {
        if (this.vertices.contains(edge.getOrigin()) && edge.getOrigin().hasNeighbour(edge.getDestination())) {
            return false;
        } else {
            this.edges.add(edge);
            return true;
        }
    }

//    public List<List<T>> getAdjacencyMatrix() {
//        List<List<T>> adjacencyMatrix = new ArrayList<>(this.vertices.size());
//        for (int i = 0; i < this.vertices.size(); ++i) {
//            adjacencyMatrix.add(new ArrayList<>(this.vertices.size()));
//            for (int j = 0; j < this.vertices.size(); ++j) {
//                adjacencyMatrix.get(i).add(0);
//            }
//        }
//        for (int i = 0; i < this.vertices.size(); ++i) {
//            for (int j = 0; j < this.vertices.size(); ++j) {
//                if (this.vertices.get(i).hasNeighbour(new Vertex<>(j))) adjacencyMatrix.get(i).set(j, this.vertices.get(i).);
//            }
//        }
//        return adjacencyMatrix;
//    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[");
        if (this.vertices.isEmpty()) {
            return sb.append("]").toString();
        } else {
            for (int i = 0; i < this.vertices.size() - 1; ++i) {
                sb.append(this.vertices.get(i).toString()).append(", ");
            }
            sb.append(this.vertices.get(this.vertices.size() - 1).toString()).append("]");
            return sb.toString();
        }
    }

    public int getNextVertexIndex() {
        return this.nextVertexIndex++;
    }

    @Override
    public GraphvizDecorator getDecorator() {
        return null;
    }

    public int getAmountVertices() {
        return this.vertices.size();
    }

    @Override
    public String toDot() {
        final String edgeSymbol = " -> ";
        final StringBuilder sb = new StringBuilder();
        sb.append("digraph DirectedGraph {\n");

        GraphvizDecorator decorator = this.getDecorator();
        if (decorator != null) {
            String d = decorator.getGlobalStyle();
            if (d != null) {
                sb.append(" ").append(d).append(";\n");
            }
            d = decorator.getGraphDecoration(this);
            if (d != null) {
                sb.append(" graph [").append(d).append("];\n");
            }
        }

        for (final Vertex<T> node : this.vertices) {
            sb.append(" \"").append(node.getIndex()).append("\" [label=\"").append(node.getLabel()).append("\"];\n");
//            decorator = node.getDecorator();
//            dot.append((decorator != null) ? decorator.getFullNodeDecoration(node) : "shape=circle";

//            final double[] p = node.getPosition();
//            if (p != null) {
//                dot.append(",pos=\"" + p[0] + "," + p[1] + "\",pin=true,";
//            }
        }

        sb.append("\n");

        for (final Edge edge : this.edges) {
            decorator = edge.getDecorator();
            sb.append(" \"").append(edge.getOrigin().getIndex()).append("\"").append(edgeSymbol).append("\"").append(edge.getDestination().getIndex()).append("\" ");
            final String label = edge.getLabel();
            sb.append((label != null) ? "[label=\"" + label + "\""/*"," */ : "");
//            sb.append("weight=").append(edge.getWeight());
            sb.append((decorator != null) ? decorator.getFullEdgeDecoration(edge) : "");
            sb.append("];\n");
        }

        sb.append("}");
        System.out.println(sb);
        return sb.toString();
    }
}
