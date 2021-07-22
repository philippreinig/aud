package aud.example.graph;

/** implements Prim's algorithm by defining {@link #priority}*/
public class PrimMinimumSpanningTree extends PriorityFirstSearch {

  public PrimMinimumSpanningTree(MyGraph g) {
    super(g);
  }

  @Override protected double priority(MyNode node, MyEdge e) {
    assert(!Double.isInfinite(node.d));
    return (e.hasWeight() ? e.getWeight() : 1.0);
  }

  @Override public String name() { return "PFS (Prim MST)"; }

}
