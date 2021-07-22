package aud.example.graph;

import java.io.File;

import aud.*;
import aud.graph.matrix.*;
import aud.util.DotViewer;
import aud.util.SingleStepper;

/** Example for computing the maximum flow using a Ford-Fulkerson type algorithm.
 *  <p>
 *  Actually, the class implements the Edmonds-Karp algorithm, which uses a
 *  breadth-first search to find augmenting paths.
 *  <p>
 *  The main part of the algorithm is found in {@link #computeMaxFlow} calling
 *  {@code #findAugmentingPath} and {@code #addPath}
 *  <p>
 *  The implementation manipulates the graph and temporarily adds backward
 *  edges to model the return flow.
 */
public class MaxFlowExample extends SingleStepper {

  /** flow times pen width * {@code WIDTHFACTOR} for visualization */
  double widthfactor = 3.0;

  protected DotViewer viewer_ = DotViewer.displayWindow
    ((String) null,"aud.example.graph.MaxFlowExample");

  @Override protected void onHalt() {
      viewer_.display(g_);
  }

  /** create application */
  public MaxFlowExample() {
    super("aud.example.graph.MaxFlowExample");
  }

  /** the graph */
  MyGraph g_ = null;

  /** the source */
  MyNode  s_ = null;

  /** the sink */
  MyNode t_ = null;

  /** the flow */
  SparseMatrix<Double> flow_ = null;

  /** get flow */
  double getFlow(MyNode i,MyNode j) {
    return flow_.get(i.index(),j.index());
  }
  /** set flow */
  void setFlow(MyNode i,MyNode j,double f) {
    flow_.set(i.index(),j.index(),f);
  }

  /** main algorithm (calls {@link #findAugmentingPath} and {@link #addPath} */
  public void computeMaxFlow(MyGraph g) {
    initialize(g);

    double cp;

    while (!Double.isInfinite(cp=findAugmentingPath())) {
      addPath(cp);
    }

    cleanup();
  }

  /** Find augmenting path using BFS and return capacity.<p>
   *  The path from source {@code s_} to sink {@code t_} is encoded in
   *  {@link MyNode#p}.
   *  @return capacity of path (minimum of edge capacities) <b>or</b>
   *  infinity if there was no path found
   */
  double findAugmentingPath() {

    for (MyNode n : g_) {
      n.p=null;
      n.d=Double.POSITIVE_INFINITY; // measure capacity of path
    }
    Queue<MyNode> open=new Queue<MyNode>();
    open.enqueue(s_);
    s_.p=s_;                        // mark as visited

    while (!open.is_empty()) {
      MyNode u=open.dequeue();

      if (u==t_)
        break;

      for (MyEdge e : g_.getOutEdges(u)) {
        MyNode v=(MyNode) e.destination();

        double capacity=e.getWeight();
        double residual=capacity-getFlow(u,v);

        if (v.p==null && residual>0.0) {
          v.p=u;
          v.d=(u.d<=residual ? u.d : residual);  // adjust capacity of path

          open.enqueue(v);
        }
      }
    }
    s_.p=null;

    assert((t_.p==null &&  Double.isInfinite(t_.d)) ||
           (t_.p!=null && !Double.isInfinite(t_.d)));

    return t_.d;
  }

  /** add result of {@link #findAugmentingPath} to flow */
  void addPath(double cp) {
    assert(t_.p!=null && !Double.isInfinite(t_.d));
    assert(t_.d==cp);

    MyNode v=t_,u=t_.p;

    do {
      MyEdge fe=g_.getEdge(u,v);
      MyEdge be=g_.getEdge(v,u);

      if (be==null) {
        be=g_.addEdge(v,u);
        be.setWeight(0.0); // flow along back edge may be allowed even though!
        be.color=BACKEDGE;
      }

      double f=getFlow(u,v);

      assert(getFlow(v,u)==-f);

      f+=cp;

      setFlow(u,v, f);
      setFlow(v,u,-f);


      fe.penwidth=cp*widthfactor;
      fe.setLabel(""+f+"/"+fe.getWeight());
      be.setLabel(""+(-f)+"/"+be.getWeight());

      System.err.println(u);

      v=u;
      u=u.p;
    } while (u!=null);

    halt("add path with capacity "+cp);
  }

  static final String BACKEDGE = "red";

  /** initialize: find source/sink, add backward edges */
  void initialize(MyGraph g) {
    if (!g.isDirected())
      throw new RuntimeException("expect directed graph!");

    g_=g;
    s_=findSource(g);
    t_=findSink(g);

    halt("Compute max flow from "+s_.getLabel()+" to "+t_.getLabel());

    // initialize zero flow
    flow_=new SparseMatrix<Double>();

    for (MyEdge e : g.edges()) {
      MyNode s=(MyNode) e.source(), t=(MyNode) e.destination();
      setFlow(s,t,0.0);
    }
  }

  /** remove backward edges */
  void cleanup() {
    Vector<MyEdge> backward_edges=new Vector<MyEdge>();

    for (MyEdge e : g_.edges()) {
      if (e.getWeight()==0.0) {
          assert(e.color==BACKEDGE);
          backward_edges.push_back(e);
      }
      else {
        double f=getFlow((MyNode) e.source(),(MyNode) e.destination());
        e.penwidth=(f>0.0 ? f*widthfactor : -1.0);
        e.color=(f>0.0 ? "blue" : null);
      }
    }

    for (MyEdge e : backward_edges)
      g_.removeEdge(e);

    double f=0.0;
    for (MyEdge e : g_.getOutEdges(s_))
      f+=getFlow(s_,(MyNode) e.destination());

    halt("RESULT with max flow = "+f);
  }

  /** find source (there must not be more than one) */
  MyNode findSource(MyGraph g) {
    MyNode s=null;
    for (MyNode n : g)
      if (g.getInDegree(n)==0) {
        if (s!=null)
          throw new RuntimeException("not implemented: multiple sources!");
        s=n;
      }
    if (s==null)
      throw new RuntimeException("no source node found");
    return s;
  }
  /** find sink (there must not be more than one) */
  MyNode findSink(MyGraph g) {
    MyNode s=null;
    for (MyNode n : g)
      if (g.getOutDegree(n)==0) {
        if (s!=null)
          throw new RuntimeException("not implemented: multiple sinks!");
        s=n;
      }
    if (s==null)
      throw new RuntimeException("no sink node found");
    return s;
  }

  protected static void usage() {
    System.err.println(
          "java aud.example.graph.MaxFlowExample "+
          "[-g graphfile] [-w factor]");
    System.exit(-1);
  }

  /** create a small example graph */
  protected static MyGraph createGraph() {
    MyGraph g=new MyGraph(true);
    MyNode s=g.addNode(), t=g.addNode(), a=g.addNode(), b=g.addNode();
    s.setLabel("s");
    t.setLabel("t");
    s.setPosition(0.0,0.0);
    t.setPosition(4.0,0.0);
    a.setPosition(2.0,2.0);
    b.setPosition(2.0,-2.0);
    g.addEdge(s,a).setWeight(4.0);
    g.addEdge(s,b).setWeight(2.0);
    g.addEdge(a,b).setWeight(3.0);
    g.addEdge(a,t).setWeight(1.0);
    g.addEdge(b,t).setWeight(6.0);
    return g;
  }

  /** example (see also {@link #usage}) */
  public static void main(String[] args) {
    MyGraph g=new MyGraph(true);

    File graph=null;
    double widthfactor=2.0; // emphasize pen width for visualization

    for (int i=0;i<args.length;++i) {
      if (args[i].compareTo("-g")==0)
        graph=new File(args[++i]);
      if (args[i].compareTo("-w")==0)
        widthfactor=Double.parseDouble(args[++i]);
    }

    MaxFlowExample app=new MaxFlowExample();
    app.widthfactor=widthfactor;

    if (graph==null)
      g=createGraph();
    else {
      new GraphParser(g.getAbstractGraph()).parse(graph);
    }

    // compute max-flow
    app.computeMaxFlow(g);
    app.halt("DONE");

    System.exit(0);
  }

}
