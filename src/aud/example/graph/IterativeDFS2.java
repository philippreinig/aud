package aud.example.graph;

import aud.Stack;

/** iterative implementation of DFS
    Traversal like {@link DepthFirstSearch} but as for {@link IterativeDFS1}
    the order of processed edges differs <b>and</b> nodes are <b>marked before
    being pushed</b>. The latter means that
    <ul>
    <li>a node is never pushed to the stack twice, i.e., the stack size is
    bounded by the number of nodes, and</li>
    <li>the algorithm stops descending if a node was marked/pushed before, i.e.,
    this is not a true DFS traversal.
    </ul>
    Sedgewick (Algorithms, 2nd ed., 1988) gives this algorithm as
    <em>nonrecursive depth first search</em> but puts some emphasis on the
    difference to the recursive version.<p>

    This version is easily modified to an "optimized" {@link BreadthFirstSearch}
    by exchanging the {@link aud.Stack} by a {@link aud.Queue}. (Think of what
    is the difference if you do the same for {@link IterativeDFS1}!)

    @see IterativeDFS1
  */
public class IterativeDFS2 extends Traversal {

  public IterativeDFS2(MyGraph g) {
    super(g);
  }

  @Override public String name() { return "iterative DFS (decently cheating)"; }

  @Override public void start(MyNode s0) {
    initialize(); // reset all node attributes

    int count=0;
    Stack<MyNode> open=new Stack<MyNode>();
    s0.ord=count++; // marked
    s0.d=0.0;

    showMark(s0);
    open.push(s0);

    while (!open.is_empty()) {
      if (verbose>0)
        System.out.println("open="+open.toString());

      MyNode s=open.pop();

      showVisit(s);

      for (MyEdge e : g_.getOutEdges(s)) {

        if (verbose>1)
          System.err.println(e);

        MyNode t=(MyNode) e.destination();
        if (t==s)
            t=(MyNode) e.source(); // undirected graph

        if (t.ord<0) { // <=> not marked
          t.ord=count++;
          t.p=s;
          t.d=s.d+(e.hasWeight() ? e.getWeight() : 1.0);

          showMark(t);

          open.push(t);
        }
      }
    }
  }

}
