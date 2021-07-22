package aud.example.graph;

import aud.graph.SimpleNode;

/** node with all possible attributes that we require ;-) */
public class MyNode extends SimpleNode {

  @Override public MyNode create() { // DON'T FORGET THIS !!!
    return new MyNode();
  }

  /** time when node is  (first marked/put into front) */
  public int ord = -1;

  /** node from which node was reached (defines spanning tree) */
  public MyNode p = null;

  /** distance to start node
     (sum of weighs or edge count if no weights defined)
    */
  public double d = Double.POSITIVE_INFINITY;

  /** priority for A*-algorithm */
  public double f = Double.POSITIVE_INFINITY;

  /** color as string */
  public String color = null;

  String fAsString(String prefix) {
    if (Double.isInfinite(f)) return "";
    else return prefix+((int) f*10);
  }

  @Override public String toString() {
    return super.toString()+
      " [p="+(p!=null?p.getLabel():"null")+", d="+d+fAsString(", ")+"]";
  }

  //public String getLabel() { return super.getLabel()+fAsString(":"); }


}
