package lectures.graphs;

import java.util.ArrayList;
import java.util.List;

public class DirectedIntegerGraph {
    private ArrayList<Vertex<Integer>> vertices;

    DirectedIntegerGraph(){
        this.vertices = new ArrayList<>();
    }
    
    DirectedIntegerGraph(ArrayList<Vertex<Integer>> nodes){
        this.vertices = nodes;
    }

    public boolean addNode(Vertex<Integer> node){
        if (this.vertices.contains(node)) return false;
        else {
            this.vertices.add(node);
            return true;
        }
    }

    public boolean removeNode(Vertex<Integer> node){
        if (this.vertices.contains(node)){
            this.vertices.remove(node);
            return true;
        }
        else
            return false;
    }

     public boolean addEdge(Vertex<Integer> vertex1, Vertex<Integer> vertex2){
         if (this.vertices.contains(vertex1) && vertex1.getNeighbours().contains(vertex2))
             return false;
         else{
             vertex1.addNeighbour(vertex2);
             return true;
         }
     }

     public List<List<Integer>> getAdjacencyMatrix(){
        List<List<Integer>> adjacencyMatrix = new ArrayList<>(this.vertices.size());
        for(int i = 0; i < this.vertices.size(); ++i){
            adjacencyMatrix.add(new ArrayList<>(this.vertices.size()));
            for(int j = 0; j < this.vertices.size(); ++j){
                adjacencyMatrix.get(i).add(0);
            }
        }
        for(int i = 0; i < this.vertices.size(); ++i){
            for(int j = 0; j < this.vertices.size(); ++j){
                if (this.vertices.get(i).hasNeighbour(new Vertex<>(j))) adjacencyMatrix.get(i).set(j, 1);
//                else adjacencyMatrix.get(i).set(j, 0);
            }
        }
        return adjacencyMatrix;
     }

     @Override
     public String toString(){
        String output = "[";
        for (int i = 0; i < this.vertices.size() - 1; ++i){
            output += this.vertices.get(i).toString() + ", ";
        }
        output += this.vertices.get(this.vertices.size() - 1).toString()+ "]";
        return output;
     }
}
