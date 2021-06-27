package lectures.graphs;

public class GraphTesting {

    private static Vertex<Integer>[] createNVertices(int n){
        Vertex<Integer>[] vertices = (Vertex<Integer>[]) new  Object[n];
        for(int i = 0; i < n; ++i){
            vertices[i] = new Vertex<Integer>(i);
        }
        return vertices;
    }
;
    public static void main(String[] args) {
        DirectedIntegerGraph graph = new DirectedIntegerGraph();
        Vertex<Integer>[] vertices = createNVertices(10);

    }
}
