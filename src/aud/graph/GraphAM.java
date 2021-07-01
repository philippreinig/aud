package aud.graph;

import aud.Vector;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Graph implementation based on adjacency matrix.<p>
 * <p>
 * The implementation uses a sparse {@link AdjacencyMatrix}
 *
 * @see AdjacencyMatrix
 */
public class GraphAM<Node extends AbstractNode, Edge extends AbstractEdge>
        extends AbstractGraph<Node, Edge>
        implements Iterable<Node> {

    AdjacencyMatrix<Edge> adj_;
    TreeSet<Node> nodes_;

    /**
     * Create graph.
     *
     * @param nodeGenerator same as for {@link AbstractGraph#AbstractGraph}
     * @param edgeGenerator same as for {@link AbstractGraph#AbstractGraph}
     * @param directed      {@code true} for creating a directed graph,
     *                      {@code false} for an undirected graph
     *                      (<em>symmetric</em> {@link AdjacencyMatrix})
     */
    public GraphAM(final Node nodeGenerator, final Edge edgeGenerator, final boolean directed) {
        super(nodeGenerator, edgeGenerator);

        this.adj_ = new AdjacencyMatrix<Edge>(!directed);
        this.nodes_ = new TreeSet<Node>();
    }

    @Override
    public boolean isDirected() {
        return !this.adj_.isSymmetricMatrix();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Node addNode() {
        final Node node = (Node) this.nodeGenerator_.create();
        node.graph_ = this;
        node.index_ = this.nodes_.size();
        this.nodes_.add(node);
        return node;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Edge addEdge(final Node source, final Node destination) {
        Edge edge = this.getEdge(this.check(source), this.check(destination));

        if (edge != null) {
            throw new RuntimeException("edge " + source.index() +
                    (this.isDirected() ? "->" : "--") +
                    destination.index() + " exists");
        }

        edge = (Edge) this.edgeGenerator_.create();
        edge.graph_ = this;
        if (!this.isDirected() && source.index_ > destination.index_) {
            edge.dst_ = source;
            edge.src_ = destination;
        } else {
            edge.src_ = source;
            edge.dst_ = destination;
        }

        this.adj_.set(source.index_, destination.index_, edge);

        return edge;
    }

    @Override
    public int getNumNodes() {
        return this.nodes_.size();
    }

    @Override
    public Node getSomeNode() {
        return this.nodes_.first();
    }

    @Override
    public Iterator<Node> iterator() {
        return this.nodes_.iterator();
    }

    @Override
    public Edge getEdge(final Node source, final Node destination) {
        return this.adj_.get(source.index_, destination.index_);
    }

    @Override
    protected Node check(final Node node) {
        super.check(node); // additional assertion
        assert (this.nodes_.contains(node));
        return node;
    }

    @Override
    protected Edge check(final Edge edge) {
        super.check(edge);  // additional assertion
        assert (this.adj_.get(edge.src_.index_, edge.dst_.index_) == edge);
        return edge;
    }

    @Override
    public Vector<Edge> getInEdges(final Node node) {
        return this.adj_.getColumnEntries(this.check(node).index_);
    }

    @Override
    public Vector<Edge> getOutEdges(final Node node) {
        return this.adj_.getRowEntries(this.check(node).index_);
    }

    @Override
    public void removeNode(final Node node) {
        this.adj_.clearColumnAndRow(this.check(node).index_);
        final boolean removed = this.nodes_.remove(node);
        assert (removed);
    }

    @Override
    public void removeEdge(final Edge edge) {
        this.check(edge);
        this.adj_.set(edge.src_.index_, edge.dst_.index_, null);
    }

    @Override
    public Iterator<Edge> getEdgeIterator() {
        return this.adj_.iterator();
    }
}
