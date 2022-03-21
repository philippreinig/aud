package aud.example.graph;

import aud.Queue;
import aud.Vector;
import aud.graph.matrix.SparseMatrix;
import aud.util.DotViewer;
import aud.util.SingleStepper;

import java.io.File;

/**
 * Example for computing the maximum flow using a Ford-Fulkerson type algorithm.
 * <p>
 * Actually, the class implements the Edmonds-Karp algorithm, which uses a
 * breadth-first search to find augmenting paths.
 * <p>
 * The main part of the algorithm is found in {@link #computeMaxFlow} calling
 * {@code #findAugmentingPath} and {@code #addPath}
 * <p>
 * The implementation manipulates the graph and temporarily adds backward
 * edges to model the return flow.
 */
public class MaxFlowExample extends SingleStepper {

    static final String BACKEDGE = "red";
    protected DotViewer viewer_ = DotViewer.displayWindow
            ((String) null, "aud.example.graph.MaxFlowExample");
    /**
     * flow times pen width * {@code WIDTHFACTOR} for visualization
     */
    double widthfactor = 3.0;
    /**
     * the graph
     */
    MyGraph g_ = null;
    /**
     * the source
     */
    MyNode s_ = null;
    /**
     * the sink
     */
    MyNode t_ = null;
    /**
     * the flow
     */
    SparseMatrix<Double> flow_ = null;

    /**
     * create application
     */
    public MaxFlowExample() {
        super("aud.example.graph.MaxFlowExample");
    }

    protected static void usage() {
        System.err.println(
                "java aud.example.graph.MaxFlowExample " +
                        "[-g graphfile] [-w factor]");
        System.exit(-1);
    }

    /**
     * create a small example graph
     */
    protected static MyGraph createGraph() {
        final MyGraph g = new MyGraph(true);
        final MyNode s = g.addNode();
        final MyNode t = g.addNode();
        final MyNode a = g.addNode();
        final MyNode b = g.addNode();
        s.setLabel("s");
        t.setLabel("t");
        s.setPosition(0.0, 0.0);
        t.setPosition(4.0, 0.0);
        a.setPosition(2.0, 2.0);
        b.setPosition(2.0, -2.0);
        g.addEdge(s, a).setWeight(4.0);
        g.addEdge(s, b).setWeight(2.0);
        g.addEdge(a, b).setWeight(3.0);
        g.addEdge(a, t).setWeight(1.0);
        g.addEdge(b, t).setWeight(6.0);
        return g;
    }

    /**
     * example (see also {@link #usage})
     */
    public static void main(final String[] args) {
        MyGraph g = new MyGraph(true);

        File graph = null;
        double widthfactor = 2.0; // emphasize pen width for visualization

        for (int i = 0; i < args.length; ++i) {
            if (args[i].compareTo("-g") == 0)
                graph = new File(args[++i]);
            if (args[i].compareTo("-w") == 0)
                widthfactor = Double.parseDouble(args[++i]);
        }

        final MaxFlowExample app = new MaxFlowExample();
        app.widthfactor = widthfactor;

        if (graph == null)
            g = createGraph();
        else {
            new GraphParser(g.getAbstractGraph()).parse(graph);
        }

        // compute max-flow
        app.computeMaxFlow(g);
        app.halt("DONE");

        System.exit(0);
    }

    /**
     * find source (there must not be more than one)
     */
    static MyNode findSource(final MyGraph g) {
        MyNode s = null;
        for (final MyNode n : g)
            if (g.getInDegree(n) == 0) {
                if (s != null)
                    throw new RuntimeException("not implemented: multiple sources!");
                s = n;
            }
        if (s == null)
            throw new RuntimeException("no source node found");
        return s;
    }

    /**
     * find sink (there must not be more than one)
     */
    static MyNode findSink(final MyGraph g) {
        MyNode s = null;
        for (final MyNode n : g)
            if (g.getOutDegree(n) == 0) {
                if (s != null)
                    throw new RuntimeException("not implemented: multiple sinks!");
                s = n;
            }
        if (s == null)
            throw new RuntimeException("no sink node found");
        return s;
    }

    @Override
    protected void onHalt() {
        this.viewer_.display(this.g_);
    }

    /**
     * get flow
     */
    double getFlow(final MyNode i, final MyNode j) {
        return this.flow_.get(i.index(), j.index());
    }

    /**
     * set flow
     */
    void setFlow(final MyNode i, final MyNode j, final double f) {
        this.flow_.set(i.index(), j.index(), f);
    }

    /**
     * main algorithm (calls {@link #findAugmentingPath} and {@link #addPath}
     */
    public void computeMaxFlow(final MyGraph g) {
        this.initialize(g);

        double cp;

        while (!Double.isInfinite(cp = this.findAugmentingPath())) {
            this.addPath(cp);
        }

        this.cleanup();
    }

    /**
     * Find augmenting path using BFS and return capacity.<p>
     * The path from source {@code s_} to sink {@code t_} is encoded in
     * {@link MyNode#p}.
     *
     * @return capacity of path (minimum of edge capacities) <b>or</b>
     * infinity if there was no path found
     */
    double findAugmentingPath() {

        for (final MyNode n : this.g_) {
            n.p = null;
            n.d = Double.POSITIVE_INFINITY; // measure capacity of path
        }
        final Queue<MyNode> open = new Queue<MyNode>();
        open.enqueue(this.s_);
        this.s_.p = this.s_;                        // mark as visited

        while (!open.isEmpty()) {
            final MyNode u = open.dequeue();

            if (u == this.t_)
                break;

            for (final MyEdge e : this.g_.getOutEdges(u)) {
                final MyNode v = (MyNode) e.destination();

                final double capacity = e.getWeight();
                final double residual = capacity - this.getFlow(u, v);

                if (v.p == null && residual > 0.0) {
                    v.p = u;
                    v.d = (u.d <= residual ? u.d : residual);  // adjust capacity of path

                    open.enqueue(v);
                }
            }
        }
        this.s_.p = null;

        assert ((this.t_.p == null && Double.isInfinite(this.t_.d)) ||
                (this.t_.p != null && !Double.isInfinite(this.t_.d)));

        return this.t_.d;
    }

    /**
     * add result of {@link #findAugmentingPath} to flow
     */
    void addPath(final double cp) {
        assert (this.t_.p != null && !Double.isInfinite(this.t_.d));
        assert (this.t_.d == cp);

        MyNode v = this.t_, u = this.t_.p;

        do {
            final MyEdge fe = this.g_.getEdge(u, v);
            MyEdge be = this.g_.getEdge(v, u);

            if (be == null) {
                be = this.g_.addEdge(v, u);
                be.setWeight(0.0); // flow along back edge may be allowed even though!
                be.color = BACKEDGE;
            }

            double f = this.getFlow(u, v);

            assert (this.getFlow(v, u) == -f);

            f += cp;

            this.setFlow(u, v, f);
            this.setFlow(v, u, -f);


            fe.penwidth = cp * this.widthfactor;
            fe.setLabel("" + f + "/" + fe.getWeight());
            be.setLabel("" + (-f) + "/" + be.getWeight());

            System.err.println(u);

            v = u;
            u = u.p;
        } while (u != null);

        this.halt("add path with capacity " + cp);
    }

    /**
     * initialize: find source/sink, add backward edges
     */
    void initialize(final MyGraph g) {
        if (!g.isDirected())
            throw new RuntimeException("expect directed graph!");

        this.g_ = g;
        this.s_ = MaxFlowExample.findSource(g);
        this.t_ = MaxFlowExample.findSink(g);

        this.halt("Compute max flow from " + this.s_.getLabel() + " to " + this.t_.getLabel());

        // initialize zero flow
        this.flow_ = new SparseMatrix<Double>();

        for (final MyEdge e : g.edges()) {
            final MyNode s = (MyNode) e.source();
            final MyNode t = (MyNode) e.destination();
            this.setFlow(s, t, 0.0);
        }
    }

    /**
     * remove backward edges
     */
    void cleanup() {
        final Vector<MyEdge> backward_edges = new Vector<MyEdge>();

        for (final MyEdge e : this.g_.edges()) {
            if (e.getWeight() == 0.0) {
                assert (e.color == BACKEDGE);
                backward_edges.push_back(e);
            } else {
                final double f = this.getFlow((MyNode) e.source(), (MyNode) e.destination());
                e.penwidth = (f > 0.0 ? f * this.widthfactor : -1.0);
                e.color = (f > 0.0 ? "blue" : null);
            }
        }

        for (final MyEdge e : backward_edges)
            this.g_.removeEdge(e);

        double f = 0.0;
        for (final MyEdge e : this.g_.getOutEdges(this.s_))
            f += this.getFlow(this.s_, (MyNode) e.destination());

        this.halt("RESULT with max flow = " + f);
    }

}
