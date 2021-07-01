package lectures.graphs;

import java.util.ArrayList;
import java.util.List;

public class DirectedGraph<T> {
    private final ArrayList<Vertex<T>> vertices;

    DirectedGraph() {
        this.vertices = new ArrayList<>();
    }

    DirectedGraph(ArrayList<Vertex<T>> nodes) {
        this.vertices = nodes;
    }

    public boolean addNode(Vertex<T> node) {
        if (this.vertices.contains(node)) return false;
        else {
            this.vertices.add(node);
            return true;
        }
    }

    public boolean removeNode(Vertex<T> node) {
        if (this.vertices.contains(node)) {
            this.vertices.remove(node);
            return true;
        } else
            return false;
    }

    public boolean addEdge(Vertex<T> vertex1, Vertex<T> vertex2, double weight) {
        if (this.vertices.contains(vertex1) && vertex1.hasNeighbour(vertex2))
            return false;
        else {
            vertex1.addNeighbour(vertex2, weight);
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
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < this.vertices.size() - 1; ++i) {
            sb.append(this.vertices.get(i).toString()).append(", ");
        }
        sb.append(this.vertices.get(this.vertices.size() - 1).toString()).append("]");
        return sb.toString();
    }
}
