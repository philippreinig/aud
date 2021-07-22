package aud.example.graph;

import java.io.File;

import aud.util.*;

/** graph traversal example */
public class TraversalExample extends SingleStepper {
  protected DotViewer viewer_ = DotViewer.displayWindow
    ((String) null,"aud.example.graph.TraversalExample");

  /** the particular traversal algorithm */
  public Traversal traversal = null;

  /** create instance */
  public TraversalExample() {
    super("aud.example.graph.TraversalExample");
  }

  /** get graph (associated with {@code traversal} ) */
  public MyGraph graph() { return traversal!=null ? traversal.graph() : null; }

  @Override protected void onHalt() {
    if (graph()!=null)
      viewer_.display(graph());
  }

  protected static void usage() {
    System.err.println(
          "java aud.example.graph.TraversalExample "+
          "[-g graphfile] [-m method] [-s s0] [-d s1] [-v N]\n"+
          " -g FILE read graph from FILE\n"+
          " -m METHOD traversal {dfs,idfs1,idfs2,bfs,dijkstra,prim,astar}\n"+
          " -s START start node\n"+
          " -n N show result of every n-th step\n"+
          " -v V set verbose level (extra output)\n"+
          " -d DESTINATION destination required for A* algorithm\n");
    System.exit(-1);
  }

  /** example (see also {@link #usage}) */
  public static void main(String[] args) {
    MyGraph g=new MyGraph(false);

    File graph=null;
    String method="dfs";
    String start=null;
    String destination=null;
    int n=1;
    int verbose=0;

    for (int i=0;i<args.length;++i) {
      if (args[i].compareTo("-g")==0)
        graph=new File(args[++i]);
      else if (args[i].compareTo("-m")==0)
        method=args[++i];
      else if (args[i].compareTo("-s")==0)
        start=args[++i];
      else if (args[i].compareTo("-n")==0)
        n=Integer.parseInt(args[++i]);
      else if (args[i].compareTo("-v")==0)
        verbose=Integer.parseInt(args[++i]);
      else if (args[i].compareTo("-d")==0)
        destination=args[++i];
      else
        usage();
    }

    TraversalExample app=new TraversalExample();

    if (graph==null)
      g=new GraphP88(method.compareTo("dfs")!=0 && method.compareTo("bfs")!=0);
    else {
      new GraphParser(g.getAbstractGraph()).parse(graph);
    }

    Traversal algorithm=null;

    if (method.compareTo("dfs")==0)
      algorithm=new DepthFirstSearch(g);
    else if  (method.compareTo("idfs1")==0)
      algorithm=new IterativeDFS1(g);
    else if  (method.compareTo("idfs2")==0)
      algorithm=new IterativeDFS2(g);
    else if  (method.compareTo("bfs")==0)
      algorithm=new BreadthFirstSearch(g);
    else if  (method.compareTo("dijkstra")==0)
      algorithm=new DijkstraShortestPaths(g);
    else if  (method.compareTo("prim")==0)
      algorithm=new PrimMinimumSpanningTree(g);
    else if  (method.compareTo("astar")==0)
      algorithm=new AStarShortestPath(g);

    if (algorithm==null)
      usage();

    algorithm.singlestepper=app;
    algorithm.nsteps=n;
    algorithm.verbose=verbose;

    app.traversal=algorithm;

    MyNode s0=null;
    MyNode s1=null;

    if (start!=null) {
      for (MyNode node : g)
        if (node.getLabel().compareTo(start)==0) {
          s0=node;
          break;
        }
      if (s0==null)
        System.err.println("could not find start node s0='"+start+"'");
    }
    if (destination!=null) {
      for (MyNode node : g)
        if (node.getLabel().compareTo(destination)==0) {
          s1=node;
          break;
        }
      if (s1==null)
        System.err.println("could not find destination node s1='"+destination+"'");
    }

    if (s0==null)
      s0=app.graph().getSomeNode();

    if (method.compareTo("astar")==0) {
      if (s1==null)
        usage();

      s1.color="red"; // destination

      app.halt(algorithm.name()+" from "+s0+" to "+s1);

      // start traversal
      ((AStarShortestPath) algorithm).start(s0,s1);

      // show result from backtracking
      app.halt("shortest path");
    }
    else {
      app.halt(algorithm.name()+ " from "+s0);

      // start traversal
      algorithm.start(s0);
    }


    ((SimpleDecorator) app.graph().getDecorator()).highlightNode(null);
    ((SimpleDecorator) app.graph().getDecorator()).highlightEdge(null);

    app.halt("DONE");

    System.out.println(app.graph());

    System.exit(0);
  }
}
