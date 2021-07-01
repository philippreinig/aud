package lectures.graphs;

import aud.util.GraphvizDecorable;
import aud.util.GraphvizDecorator;
import aud.util.Graphvizable;

public class Edge implements Graphvizable, GraphvizDecorable {
    private final Vertex<?> origin;
    private final Vertex<?> destination;
    private final double weight;

    public Edge(final Vertex<?> origin, final Vertex<?> destination) {
        this(origin, destination, 1.0);
    }

    public Edge(final Vertex<?> origin, final Vertex<?> destination, final double weight) {
        if (origin == null && destination == null) {
            throw new IllegalArgumentException();
        }
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
    }

    public Vertex<?> getDestination() {
        return this.destination;
    }

    public Vertex<?> getOrigin() {
        return this.origin;
    }

    public String getLabel() {
        final double w = this.getWeight();
        if (w == Math.floor(w)) // missing proper printf, Java's formatting sucks!
        {
            return "" + ((int) this.getWeight());
        } else {
            return "" + w;
        }
    }

    public double getWeight() {
        return this.weight;
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
    public boolean equals(final Object other) {
        return other instanceof Edge && (this.getOrigin().equals(((Edge) other).getOrigin())) && (this.getDestination().equals(((Edge) other).getDestination()));
    }
}
