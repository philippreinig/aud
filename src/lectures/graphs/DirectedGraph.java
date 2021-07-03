package lectures.graphs;

import aud.util.GraphvizDecorable;
import aud.util.GraphvizDecorator;
import aud.util.Graphvizable;

import java.util.ArrayList;
import java.util.List;

public class DirectedGraph<T> implements Graphvizable, GraphvizDecorable {
    private final ArrayList<Vertex<T>> vertices;
    private final ArrayList<Edge> edges;

    private int nextVertexIndex = 0;

    public DirectedGraph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void addNode(final Vertex<T> node) {
        if (!this.vertices.contains(node)) {
            this.vertices.add(node);
        }
    }

    public void removeNode(final Vertex<T> node) {
        this.vertices.remove(node);
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public Edge[] getEdgesArray() {
        return this.edges.toArray(Edge[]::new);
    }

    public void addEdge(final Edge edge) {
        if (this.edges.contains(edge)) {
            System.out.println("edge from " + edge.getOrigin() + " to " + edge.getDestination() + " already contained in graph.");
        } else {
            this.edges.add(edge);
        }
    }


    public Double[][] getAdjacencyMatrix() {
        final int n = this.vertices.size();
        final Double[][] adjacencyMatrix = new Double[n][n];
        for (int i = 0; i < adjacencyMatrix.length; ++i) {
            for (int j = 0; j < adjacencyMatrix[i].length; ++j) {
                adjacencyMatrix[i][j] = 0.0;
            }
        }
        for (final Vertex<T> v : this.vertices) {
            for (final Edge e : v.getOutgoingEdges()) {
                adjacencyMatrix[v.getIndex()][e.getDestination().getIndex()] = e.getWeight();
            }
        }
        return adjacencyMatrix;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final Vertex<T> v : this.vertices) {
            sb.append(v).append(": ");
            final List<Edge> edgesOfV = v.getOutgoingEdges();
            if (edgesOfV.isEmpty()) {
                sb.append("\n");
            } else {
                for (int i = 0; i < edgesOfV.size() - 1; ++i) {
                    sb.append(edgesOfV.get(i).getDestination()).append(", ");
                }
                sb.append(edgesOfV.get(edgesOfV.size() - 1).getDestination()).append("\n");

            }
        }
        return sb.toString();
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

    public List<Vertex<T>> getVertices() {
        return this.vertices;
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
        }

        sb.append("\n");

        for (final Edge edge : this.edges) {
            decorator = edge.getDecorator();
            sb.append(" \"").append(edge.getOrigin().getIndex()).append("\"").append(edgeSymbol).append("\"").append(edge.getDestination().getIndex()).append("\" ");
            final String label = edge.getLabel();
            sb.append((label != null) ? "[label=\"" + label + "\""/*"," */ : "");
            sb.append((decorator != null) ? decorator.getFullEdgeDecoration(edge) : "");
            sb.append("];\n");
        }

        sb.append("}");
        return sb.toString();
    }
}
