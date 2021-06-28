package lectures.graphs;

import java.util.ArrayList;
import java.util.List;

public class GraphTesting {

    private static List<Vertex<Integer>> createNVertices(int n){
        ArrayList<Vertex<Integer>> vertices = new ArrayList<>();
        for(int i = 0; i < n; ++i){
            vertices.add(new Vertex<Integer>(i));
        }
        return vertices;
    }

    private static void printAdjacencyMatrix(List<List<Integer>> adjacencyMatrix){
        for(int i = 0; i < adjacencyMatrix.size(); ++i){
            System.out.print("[");
            if (adjacencyMatrix.size() >= 1){
                for(int j = 0; j < adjacencyMatrix.size() - 1; ++j){
                    System.out.print(adjacencyMatrix.get(i).get(j).toString() + ", ");
                }
            }
            System.out.print(adjacencyMatrix.get(i).get(adjacencyMatrix.size()-1).toString());
            System.out.println("]");
        }

    }
    public static void main(String[] args) {
        DirectedIntegerGraph graph = new DirectedIntegerGraph();
        List<Vertex<Integer>> vertices = createNVertices(10);
        for(Vertex<Integer> vertex : vertices) graph.addNode(vertex);
        System.out.println(graph);
        graph.addEdge(vertices.get(0), vertices.get(1));
        graph.addEdge(vertices.get(0), vertices.get(2));
        graph.addEdge(vertices.get(2), vertices.get(3));
        graph.addEdge(vertices.get(7), vertices.get(4));
        graph.addEdge(vertices.get(1), vertices.get(9));
        System.out.println(graph);
        printAdjacencyMatrix(graph.getAdjacencyMatrix());
    }


}