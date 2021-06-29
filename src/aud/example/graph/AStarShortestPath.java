package aud.example.graph;

import java.util.Comparator;

import aud.PriorityQueue;
import aud.HashMap;
import aud.util.SimpleDecorator;

/** implementation of the A* algorithm */
public class AStarShortestPath extends Traversal {

  /** destination */
  MyNode s1_ = null;

  /** comparator for {#link aud.PriorityQueue}: compares f-values
      (in contrast to {#link DijkstraShortestPath}
    */
  protected Comparator<MyNode> compareNodes =
     new Comparator<MyNode>() {
     @Override public int compare(MyNode a,MyNode b) {
       double d=a.f-b.f ; // compare f-values
       return (d<0.0) ? -1 : (d>0.0 ? +1 : 0);
     }
   };

  /** create instance with destination {@code s1} */
  public AStarShortestPath(MyGraph g,MyNode s1) {
    super(g);
    s1_=s1;

  }
  public AStarShortestPath(MyGraph g) {
    this(g,null);
  }

  /** heuristic that guides search */
  protected double h(MyNode node) {
    double[] a=s1_.getPosition(), b=node.getPosition();
    double dx=b[0]-a[0], dy=b[1]-a[1];
    return Math.sqrt(dx*dx+dy*dy);
  }

  @Override public String name() { return "A*"; }

  /** find shortest path to destination */
  @Override public void start(MyNode s0) {
    if (s1_==null)
      throw new RuntimeException("A* requires destination");

    initialize(); // reset all node attributes

    int n=g_.getNumNodes();
    int count=0;
    PriorityQueue<MyNode>  open=new PriorityQueue<MyNode>(n,compareNodes);
    HashMap<MyNode,Double> closed=new HashMap<MyNode,Double>(n);
    s0.d=0.0;
    s0.f=h(s0);

    showMark(s0);

    open.push(s0);

    while (!open.is_empty()) {
      if (verbose>0)
        System.out.println("open="+open.toString());

      MyNode s=open.pop();

      assert(!Double.isInfinite(s.d));
      closed.insert(s,s.d);
      showVisit(s);

      if (s==s1_) {
        if (verbose>0)
          System.out.println("reached destination '"+s.getLabel()+"'");

        ((SimpleDecorator) g_.getDecorator()).unmarkNode(s);
        ((SimpleDecorator) g_.getDecorator()).highlightNode(null);
        ((SimpleDecorator) g_.getDecorator()).highlightEdge(null);
        s.color="red"; // destination

        break;
      }

      for (MyEdge e : g_.getOutEdges(s)) {

        if (verbose>0)
          System.err.println(e);

        MyNode t=(MyNode) e.destination();
        if (t==s)
            t=(MyNode) e.source();          // undirected graph

        if (!closed.contains(t)) {

          double dt=s.d+(e.hasWeight() ? e.getWeight() : 1.0);
          double pr=dt+h(t);

          boolean push=Double.isInfinite(t.d);

          if (dt<t.d) {
            t.ord=count++;
            t.p=s;
            t.d=dt;
            t.f=pr;

            showMark(t);

            if (push)
              open.push(t);
            else
              open.lower(t);
          }

          if (verbose>0)
            System.out.println("open="+open.toString());
        }
      }
    }

    // backtracking: output from destination to source

    ((SimpleDecorator) g_.getDecorator()).unmarkAllEdges();
    System.out.println("backtracked path: ");

    MyNode node=s1_;

    while (node!=s0) {
      System.out.print(node.toString()+" ");
      if (node!=s1_) {
        node.color="red";
        ((SimpleDecorator) g_.getDecorator()).unmarkNode(node);
      }
      g_.getEdge(node.p,node).color="red";
      g_.getEdge(node.p,node).penwidth=4.0;

      node=node.p;
    }
    node.color="red";
    ((SimpleDecorator) g_.getDecorator()).unmarkNode(node);
    System.out.println(s0);
  }

  /** find shortest path from {@code s0} to {@code s1} */
  public void start(MyNode s0,MyNode s1) {
    s1_=s1;
    start(s0);
  }
}
