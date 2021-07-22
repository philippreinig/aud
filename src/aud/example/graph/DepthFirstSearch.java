package aud.example.graph;

/** Standard recursive implementation of depth first search.<p>
    This is the classic implementation of DFS. In addition, we provide two
    nonrecursive variants.
    <ul>
    <li>{@link IterativeDFS1} is close to the recursive implementation (but
    reversing the order of examined edges)</li>
    <li>{@link IterativeDFS2} takes a "short cut" by marking nodes early. This
    leads to a different traversal.</li>
    </ul>
    @see IterativeDFS1
    @see IterativeDFS2
  */
public class DepthFirstSearch extends Traversal {

  public DepthFirstSearch(MyGraph g) {
    super(g);
  }

  @Override public String name() { return "recursive DFS"; }

  private int count_ = 0;

  @Override public void start(MyNode s0) {

    initialize(); // reset all node attributes

    count_ = 0;
    s0.d=0.0;

    dfs(s0);
  }

  protected void dfs(MyNode s) {
    s.ord=count_++; // marked
    showMark(s);
    showVisit(s);

    if (verbose>0)
      System.out.println("s="+s);

    for (MyEdge e : g_.getOutEdges(s)) {

      if (verbose>1)
        System.err.println(e);

      MyNode t=(MyNode) e.destination();
      if (t==s)
          t=(MyNode) e.source(); // undirected graph

      if (t.ord<0) { // <=> not marked
        t.d=s.d+(e.hasWeight() ? e.getWeight() : 1.0);
        t.p=s;
        dfs(t);
      }
    }
  }
}
