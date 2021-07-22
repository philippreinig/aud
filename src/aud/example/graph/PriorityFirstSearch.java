package aud.example.graph;

import java.util.Comparator;
import aud.PriorityQueue;

/** Priority first search implementation. The {@link #priority} function
    must be implemented (e.g., Dijkstra, Prim}.
  */
public abstract class PriorityFirstSearch extends Traversal {

  /** comparator for {#link aud.PriorityQueue} */
  protected Comparator<MyNode> compareNodes =
    new Comparator<MyNode>() {
    @Override public int compare(MyNode a,MyNode b) {
      double d=a.d-b.d ;
      return (d<0.0) ? -1 : (d>0.0 ? +1 : 0);
    }
  };

  public PriorityFirstSearch(MyGraph g) {
    super(g);
  }

  /** Compute priority of a node:
      <ul>
      <li>{@code node.d+e.getWeight()} for Dijkstra's algorithm to
      find <b>shortest paths</b> (and the shortest path tree)</li>
      <li>{@code e.getWeight()} for Prim's algorithm to
      find the <b>minimum spanning tree</b></li>
      </ul>
    */
  protected abstract double priority(MyNode node,MyEdge e);

  @Override public void start(MyNode s0) {
    initialize(); // reset all node attributes

    int count=0;
    PriorityQueue<MyNode> open=
      new PriorityQueue<MyNode>(g_.getNumNodes(),compareNodes);
    s0.d=0.0;

    showMark(s0);

    open.push(s0);

    while (!open.is_empty()) {
      if (verbose>0)
        System.out.println("open="+open.toString());

      MyNode s=open.pop();

      assert(!Double.isInfinite(s.d));
      showVisit(s);

      for (MyEdge e : g_.getOutEdges(s)) {

        if (verbose>0)
          System.err.println(e);

        MyNode t=(MyNode) e.destination();
        if (t==s)
            t=(MyNode) e.source();          // undirected graph

        double pr=priority(s,e);

        if (Double.isInfinite(t.d)) {

          assert(!open.contains(t));

          t.ord=count++;
          t.p=s;
          t.d=pr;

          showMark(t);

          open.push(t);
        }
        else if (t.d>pr && open.contains(t)) {   // update distance/priority

          if (verbose>0)
            System.out.println("update "+t+": priority "+t.d+" -> "+pr+
                 ", parent "+t.p+" -> "+s);

          t.p=s;
          t.d=pr;

          open.lower(t);

          if (verbose>0)
            System.out.println("open="+open.toString());
        }
      }
    }
  }

}
