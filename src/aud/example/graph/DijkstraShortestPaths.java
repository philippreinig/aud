package aud.example.graph;

/** implements Dijkstra's algorithm by defining {@link #priority} */
public class DijkstraShortestPaths extends PriorityFirstSearch {

  public DijkstraShortestPaths(MyGraph g) {
    super(g);
  }

  @Override protected double priority(MyNode node,MyEdge e) {
    assert(!Double.isInfinite(node.d));
    return node.d+(e.hasWeight() ? e.getWeight() : 1.0);
  }

  @Override public String name() { return "PFS (Dijkstra)"; }
}
