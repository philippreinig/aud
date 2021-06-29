package aud.example.graph;

import aud.util.*;

/** interface for traversals of MyGraph */
public abstract class Traversal {
  protected MyGraph g_ = null;
  protected int time_  = 0;

  /** may halt if single stepper was set */
  public SingleStepper singlestepper = null;
  /** halt every nsteps steps in {@code time_} */
  public int nsteps = 1;
  /** set verbosity (extra output if >0) */
  public int verbose = 0;

  /** initiate traversal of {@code g} */
  public Traversal(MyGraph g) {
    g_=g;
  }

  /** get graph */
  MyGraph graph() { return g_; }

  /** get traversal name */
  public abstract String name();

  /** start traversal at node s0 */
  public abstract void start(MyNode s0);

  /** initialize graph for traversal (reset all attributes),
      provided for convenience to be called by {@link #start}
    */
  protected void initialize() {
    for (MyNode node : g_) {
      node.ord=-1;
      node.p=null;
      node.d=Double.POSITIVE_INFINITY;
      node.f=Double.POSITIVE_INFINITY;
    }
  }

  /** callback to give visual feedback on marking a node */
  public void showMark(MyNode node) {
    node.color="lightblue";
  }

  /** callback to give visual feedback on visiting a node */
  void showVisit(MyNode node) {
    ((SimpleDecorator) g_.getDecorator()).markNode(node);
    ((SimpleDecorator) g_.getDecorator()).highlightNode(node);
    if (node.p!=null) {
      MyEdge e=g_.getEdge(node.p,node);
      ((SimpleDecorator) g_.getDecorator()).markEdge(e);
      ((SimpleDecorator) g_.getDecorator()).highlightEdge(e);
    }
    if (time_++%nsteps==0 && singlestepper!=null)
      singlestepper.halt("visit "+node.toString());
  }
}
