package lectures.graphs;

public class Edge {
    private final Vertex<?> origin;
    private final Vertex<?> destination;
    private final double weight;

    public Edge(Vertex<?> origin, Vertex<?> destination){
        this(origin, destination, 1.0);
    }

    public Edge(Vertex<?> origin, Vertex<?> destination, double weight){
        if (origin == null && destination == null) throw new IllegalArgumentException();
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
    }

    public Vertex<?> getDestination(){
        return this.destination;
    }

    public Vertex<?> getOrigin() {
        return this.origin;
    }

    public double getWeight(){
        return this.weight;
    }
}
