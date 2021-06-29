package aud.example.graph;

/**
 * Standard recursive implementation of depth first search.<p>
 * This is the classic implementation of DFS. In addition, we provide two
 * nonrecursive variants.
 * <ul>
 * <li>{@link IterativeDFS1} is close to the recursive implementation (but
 * reversing the order of examined edges)</li>
 * <li>{@link IterativeDFS2} takes a "short cut" by marking nodes early. This
 * leads to a different traversal.</li>
 * </ul>
 *
 * @see IterativeDFS1
 * @see IterativeDFS2
 */
public class DepthFirstSearch extends Traversal {

    private int count = 0;

    public DepthFirstSearch(final MyGraph g) {
        super(g);
    }

    @Override
    public String name() {
        return "recursive DFS";
    }

    @Override
    public void start(final MyNode s0) {

        this.initialize(); // reset all node attributes

        this.count = 0;
        s0.d = 0.0;

        this.dfs(s0);
    }

    protected void dfs(final MyNode s) {
        s.ord = this.count++; // marked
        this.showMark(s);
        this.showVisit(s);

        if (this.verbose > 0) {
            System.out.println("s=" + s);
        }

        for (final MyEdge e : this.g_.getOutEdges(s)) {

            if (this.verbose > 1) {
                System.err.println(e);
            }

            MyNode t = (MyNode) e.destination();
            if (t == s) {
                t = (MyNode) e.source(); // undirected graph
            }

            if (t.ord < 0) { // <=> not marked
                t.d = s.d + (e.hasWeight() ? e.getWeight() : 1.0);
                t.p = s;
                this.dfs(t);
            }
        }
    }
}
