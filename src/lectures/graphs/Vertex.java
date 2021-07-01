package lectures.graphs;

import java.util.ArrayList;
import java.util.List;


class Vertex<T> {
    private final ArrayList<Edge> outgoingEdges;

    private final T data;

    Vertex(T data) {
        this(null, data);
    }

    Vertex(ArrayList<Edge> outgoingEdges, T data) {
        if (data == null) throw new IllegalArgumentException("data may not be null!");
        this.outgoingEdges = outgoingEdges != null ? outgoingEdges : new ArrayList<>();
        this.data = data;
    }

    public List<Edge> getoutgoingEdges() {
        return this.outgoingEdges;
    }

    public void addOutgoingEdge(Edge edge){
        if (edge == null) throw new IllegalArgumentException();
        this.outgoingEdges.add(edge);
    }

    public boolean addNeighbour(Vertex<T> neighbour, double weight) {
        for(Edge edge : outgoingEdges){
            if (edge.getDestination().equals(neighbour)) return false;
        }
        this.outgoingEdges.add(new Edge(this, neighbour, weight));
        return true;
    }

    public boolean removeNeighbour(Vertex<T> neighbour) {
        for(Edge edge : outgoingEdges){
            if (edge.getDestination().equals(neighbour)) {
                this.outgoingEdges.remove(edge);
                return true;
            }
        }
        return false;
    }

    public boolean hasNeighbour(Vertex<T> neighbour) {
        for(Edge edge : outgoingEdges){
            if (edge.getDestination().equals(neighbour)) return true;
        }
        return false;
    }

    public T getData() {
        return this.data;
    }


    @Override
    public boolean equals(Object other) {
        return other instanceof Vertex<?> && this.getData().equals(((Vertex<?>) other).getData());
    }

    @Override
    public String toString() {
        var output = new StringBuilder("\u001B[36m" + "(" + this.data.toString() + ")" + "\u001B[0m");
        if (this.outgoingEdges.size() == 0) {
            output.append("[]");
        } else {
            output.append("[");
            for (int i = 0; i < this.outgoingEdges.size() - 1; ++i) {
                output.append(this.outgoingEdges.get(i).getDestination().toStringJustData()).append(", ");
            }
            output.append(this.outgoingEdges.get(this.outgoingEdges.size() - 1).getDestination().toStringJustData());
            output.append("]");
        }
        return output.toString();
    }

    public String toStringJustData() {
        return "(" + this.data.toString() + ")";
    }
}