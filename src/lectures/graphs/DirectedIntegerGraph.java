package lectures.graphs;

import java.util.ArrayList;

public class DirectedIntegerGraph {
    private ArrayList<Vertex<Integer>> nodes;

    DirectedIntegerGraph(){
        this.nodes = new ArrayList<>();
    }
    
    DirectedIntegerGraph(ArrayList<Vertex<Integer>> nodes){
        this.nodes = nodes;
    }

    public boolean addNode(Vertex<Integer> node){
        if (this.nodes.contains(node)) return false;
        else {
            this.nodes.add(node);
            return true;
        }
    }

    public boolean removeNode(Vertex<Integer> node){
        if (this.nodes.contains(node)){
            this.nodes.remove(node);
            return true;
        }
        else
            return false;
    }

    // public void addEdge(Vertex<Integer> vertex1, Vertex<Integer> vertex2){
    //     if (this.nodes.contains(vertex1) && vertex1.){
    //         vertex1.addNeighbour(vertex2);
    // }

    
}
