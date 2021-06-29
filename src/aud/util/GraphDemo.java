package aud.util;

import aud.graph.*;

/** Demonstrate visualization of graph algorithms
 */
public class GraphDemo extends SingleStepper {

  protected AbstractGraph<?,?> g = null;
  protected DotViewer          v = DotViewer.displayWindow((String) null,
                                                       "aud.util.GraphDemo");

  static class MyGraph extends GraphAM<SimpleNode,SimpleEdge> {
    final GraphvizDecorator decorator = new SimpleDecorator();

    MyGraph() {
      super(new SimpleNode(),new SimpleEdge(),true);
    }
    @Override public GraphvizDecorator getDecorator() {
      return decorator;
    }
  }

  GraphDemo(AbstractGraph<?,?> g) {
    super("aud.util.GraphDemo");
    this.g=g;
  }

  protected void onHalt() {
    if (g!=null)
      v.display(g);
  }

  public static void main(String args[]) {

    MyGraph g=new MyGraph();
    GraphDemo app=new GraphDemo(g);

    SimpleNode n0=g.addNode();       app.halt();
    SimpleNode n1=g.addNode();       app.halt();
    SimpleNode n2=g.addNode();       app.halt();

    SimpleEdge e0=g.addEdge(n0,n1);  app.whereAmI().showSource().halt("add e0");
    SimpleEdge e1=g.addEdge(n1,n0);  app.halt("add e1");

    SimpleEdge e2=g.addEdge(n0,n2);  app.halt("add e2");
    SimpleEdge e3=g.addEdge(n2,n1);  app.halt("add e3");

    System.out.println("n0="+n0);
    System.out.println("n1="+n1);
    System.out.println("n2="+n2);
    System.out.println("e0="+e0);
    System.out.println("e1="+e1);
    System.out.println("e2="+e2);
    System.out.println("e3="+e3);

    SimpleDecorator decorator=(SimpleDecorator) g.getDecorator();
    decorator.markNode(n0); app.halt();
    n0.setLabel("A");
    e0.setLabel("a b c");
    decorator.markEdge(e0); app.halt();
    decorator.unmarkNode(n0);
    decorator.markNode(n1); app.halt();
    decorator.highlightNode(n2);
    decorator.highlightEdge(e2); app.halt();

    app.halt("QUIT");
    System.exit(0);
  }

}
