package aud.example.graph;

import aud.util.SimpleDecorator;
import aud.util.GraphvizDecorable;

public class MyDecorator extends SimpleDecorator {

  MyGraph g_;

  public MyDecorator(MyGraph g) {
    g_=g;
  }

  @Override public String getNodeDecoration(GraphvizDecorable object) {
    String decoration=super.getNodeDecoration(object);
    if ((decoration==null || !decoration.contains("fillcolor=")) &&
        ((MyNode) object).color!=null){
      decoration=(decoration==null ? "" : decoration+",")+
        "style=filled,fillcolor="+((MyNode) object).color;
    }
    return decoration;
  }

  @Override public String getEdgeDecoration(GraphvizDecorable object) {
    String decoration=super.getEdgeDecoration(object);
    if ((decoration==null || !decoration.contains("color=")) &&
        ((MyEdge) object).color!=null) {
      decoration=(decoration==null ? "" : decoration+",")+
        "color="+((MyEdge) object).color;
    }
    if (((MyEdge) object).penwidth>=0.0) {
      decoration=(decoration==null ? "" : decoration+",")+
        "penwidth="+((MyEdge) object).penwidth;
    }
    return decoration;
  }
}
