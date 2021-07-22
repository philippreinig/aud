package aud.example.graph;

import aud.Queue;

/** implements BFS */
public class BreadthFirstSearch extends Traversal {

  public BreadthFirstSearch(MyGraph g) {
    super(g);
  }

  @Override public String name() { return "BFS"; };

  @Override public void start(MyNode s0) {
    initialize(); // reset all node attributes

    int count=0;
    Queue<MyNode> open=new Queue<MyNode>();
    s0.ord=count++;
    s0.d=0.0;

    showMark(s0);

    open.enqueue(s0);

    while (!open.is_empty()) {
      if (verbose>0)
        System.out.println("open="+open.toString());

      MyNode s=open.dequeue();

      showVisit(s);

      for (MyEdge e : g_.getOutEdges(s)) {

        if (verbose>0)
          System.err.println(e);

        MyNode t=(MyNode) e.destination();
        if (t==s)
            t=(MyNode) e.source(); // undirected graph

        if (t.ord<0) { // <=> not marked
          t.ord=count++;
          t.p=s;
          t.d=s.d+(e.hasWeight() ? e.getWeight() : 1.0);

          showMark(t);

          open.enqueue(t);
        }
      }
    }
  }

}
