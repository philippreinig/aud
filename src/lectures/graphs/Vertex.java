package lectures.graphs;

import aud.util.GraphvizDecorable;
import aud.util.GraphvizDecorator;
import aud.util.Graphvizable;

import java.util.ArrayList;
import java.util.List;


class Vertex<T> implements Graphvizable, GraphvizDecorable, Comparable<T> {
    private final ArrayList<Edge> outgoingEdges;
    private final T data;
    private final Graph<T> graph;

    private final int index;


    public Vertex(final Graph<T> graph, final T data) {
        this(graph, data, new ArrayList<>());
    }

    public Vertex(final Graph<T> graph, final T data, final List<Edge> outgoingEdges) {
        if (data == null) {
            throw new IllegalArgumentException("data may not be null!");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph may not be null!");
        }
        this.graph = graph;
        this.data = data;
        this.outgoingEdges = new ArrayList<>(outgoingEdges);
        this.index = this.graph.getNextVertexIndex();
    }

    public Graph<T> getGraph() {
        return this.graph;
    }

    public List<Edge> getOutgoingEdges() {
        return this.outgoingEdges;
    }

    public int getIndex() {
        return this.index;
    }

    public boolean addOutgoingEdge(final Edge edge) {
        if (this.outgoingEdges.contains(edge)) {
            return false;
        }
        this.outgoingEdges.add(edge);

        return true;
    }

    public boolean removeNeighbour(final Vertex<T> neighbour) {
        for (final Edge edge : this.outgoingEdges) {
            if (edge.getDestination().equals(neighbour)) {
                this.outgoingEdges.remove(edge);
                return true;
            }
        }
        return false;
    }

    public boolean hasNeighbour(final Vertex<?> neighbour) {
        for (final Edge edge : this.outgoingEdges) {
            if (edge.getDestination().equals(neighbour)) {
                return true;
            }
        }
        return false;
    }

    public T getData() {
        return this.data;
    }


    @Override
    public boolean equals(final Object other) {
        return other instanceof Vertex<?> && this.getData().equals(((Vertex<?>) other).getData());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        final var output = new StringBuilder(this.data.toString());
        if (this.outgoingEdges.isEmpty()) {
            output.append("[]");
        } else {
            output.append("[");
            for (int i = 0; i < this.outgoingEdges.size() - 1; ++i) {
                output.append(this.outgoingEdges.get(i).getDestination().getLabel()).append(", ");
            }
            output.append(this.outgoingEdges.get(this.outgoingEdges.size() - 1).getDestination().getLabel());
            output.append("]");
        }
        return output.toString();
    }

    public String getLabel() {
        return this.data.toString();
    }

    @Override
    public GraphvizDecorator getDecorator() {
        return null;
    }

    @Override
    public String toDot() {
        return null;
    }

    @Override
    public int compareTo(final Object o) {
        if (!(o instanceof Vertex)) {
            throw new IllegalArgumentException();
        } else {
            return this.index - ((Vertex<?>) o).index;
        }
    }
}