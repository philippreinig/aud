package aud.example.graph;

import aud.Stack;

/** Iterative implementation of DFS.<p>
    Traversal like {@link DepthFirstSearch} but order of processed edges
    differs. We use a second stack to keep track of the parent node in the
    spanning tree. (This could be solved differently. Note, however, that we
    have to transfer the "complete state" near push.) <p>
    In contrast to {@link IterativeDFS2}, nodes are <b>marked</b> only when
    they are popped off the stack. This simulates the recursive
    {@link DepthFirstSearch} best, but allows to the stack size to grow beyond
    the number of nodes!

    @see IterativeDFS2
  */
public class IterativeDFS1 extends Traversal {

  public IterativeDFS1(MyGraph g) {
    super(g);
  }

  @Override public String name() { return "iterative DFS (close to DFS)"; };

  @Override public void start(MyNode s0) {
    initialize(); // reset all node attributes

    int count=0;
    Stack<MyNode>  open=new Stack<MyNode>();
    Stack<MyNode>  parent=new Stack<MyNode>();

    s0.d=0.0;
    open.push(s0);
    parent.push(null);

    while (!open.is_empty()) {
      if (verbose>0)
        System.out.println("open="+open.toString());

      MyNode s=open.pop();
      MyNode p=parent.pop();

      if (s.ord<0) {
        s.ord=count++; // marked
        s.p=p;

        if (p!=null) {
          MyEdge e=g_.getEdge(p,s);
          s.d=p.d+(e.hasWeight() ? e.getWeight() : 1.0);
        }

        showMark(s);
        showVisit(s);
      }

      for (MyEdge e : g_.getOutEdges(s)) {

        if (verbose>1)
          System.err.println(e);

        MyNode t=(MyNode) e.destination();
        if (t==s)
            t=(MyNode) e.source(); // undirected graph

        if (t.ord<0) {              // not marked
          open.push(t);
          parent.push(s);
        }
      }
    }
  }

}
