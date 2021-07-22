package aud.example.graph;

import java.io.File;
import aud.graph.*;
import aud.HashMap;
import aud.util.Sys;
import aud.util.DotViewer;

/** Parse text to build graph.<p>

    <pre><code>
     graph  ::= edges
     edges  ::= edge | edges edge
     edge   ::= ( node | node ( '--' | '->' ) weight node )
     node   ::= IDENTIFIER | IDENTIFIER pos
     pos    ::= '@' '(' NUMBER ',' NUMBER ')'
     weight ::= NIL | '(' NUMBER ')'
    </code></pre>

    @see Tokenizer
    @see aud.graph.AbstractGraph
 */
public class GraphParser {

  Tokenizer scanner_  = null;
  boolean verbose_  = false;
  AbstractGraph<AbstractNode,AbstractEdge> graph_ = null;
  HashMap<String,AbstractNode> nodeMap_  = null;

  /** create new parser for graph {@code g} */
  public GraphParser(AbstractGraph<AbstractNode,AbstractEdge> g) {
    graph_=g;
  }

  /** parse input
      @param input text representation of parser
      @throws RuntimeException on syntax error or invalid operation
   */
  public void parse(String input) {
    scanner_ = new Tokenizer(input);
    scanner_.next(); // use as "lookahead"
    nodeMap_=new HashMap<String,AbstractNode>();
    graph(0);
    expect(Tokenizer.END_OF_INPUT,"<end of input>");
  }

  /** {@link #parse(String)} contents of file */
  public void parse(File file) {
    parse(Sys.readFile(file));
  }

  /** set verbose mode (report state to {@code System.err}) */
  public void setVerbose(boolean b) {
    verbose_=b;
  }

  /** helper: generate a simple error message */
  protected String errorNear() {
    return "near '"+scanner_.matchedText()+
      "'\nbefore '"+scanner_.remainder()+"'";
  }

  /** helper: "lookahead" is the usual phrasing */
  protected int lookahead() {
    return scanner_.matchedTokenId();
  }

  /** helper: consume current token and advance to next token */
  protected void nextToken() {
    if (scanner_.next()==Tokenizer.NO_MATCH)
      throw new RuntimeException("unknown token: lexcial analysis failed at '"+
			    scanner_.remainder()+"'");
  }

  /** helper: check token (without calling {@link #nextToken}!)
      @throws SyntaxErrior if token does not match
   */
  protected void expect(int tokenid,String text) {
    if (lookahead()!=tokenid)
      throw new RuntimeException("expected '"+text+"' got '"+
			    scanner_.matchedText()+"'\nbefore '"+
			    scanner_.remainder());
  }

  /** helper: print out information */
  protected void verbose(int level,String message) {
    if (verbose_) {
      System.err.println(Sys.indent(level)+message+
			 " ['"+scanner_.matchedText()+"','"+
			 scanner_.remainder()+"']");
    }
  }

  //
  // the parser
  //

  /** parse list of nodes/edges */
  protected void graph(int level) {
    verbose(level,"<graph>");

    while (lookahead()!=Tokenizer.END_OF_INPUT)
      edge(level+1);
  }

  /** parse edge */
  protected AbstractEdge edge(int level) {
    verbose(level,"<edge>");
    AbstractNode from=node(level+1);
    if (lookahead()!=Tokenizer.EDGE && lookahead()!=Tokenizer.DEDGE)
      return null;

    boolean directed=(lookahead()==Tokenizer.DEDGE);

    if (directed ^ graph_.isDirected())
      throw new RuntimeException("specified directed edge "+
                                 "for undirected graph or vice versa");

    nextToken();

    Double w=weight(level+1);

    AbstractNode to=node(level+1);
    AbstractEdge e=graph_.addEdge(from,to);

    if (w!=null)
      e.setWeight(w);

    return e;
  }

  /** parse node */
  protected AbstractNode node(int level) {
    verbose(level,"<node>");
    if (lookahead()!=Tokenizer.IDENTIFIER &&
        lookahead()!=Tokenizer.NUMBER)
      expect(Tokenizer.IDENTIFIER,"node identifier");
    String id=scanner_.matchedText();
    nextToken();

    AbstractNode n=nodeMap_.find(id);

    if (n==null) {
      n=graph_.addNode();
      n.setLabel(id);
      nodeMap_.insert(id,n);
    }

    if (lookahead()==Tokenizer.AT) {
      double[] p=pos(level+1);
      n.setPosition(p[0],p[1]);
    }

    return n;
  }

  /** parse position */
  protected double[] pos(int level) {
    verbose(level,"<pos>");
    expect(Tokenizer.AT,"@(x,y)");
    nextToken();
    expect(Tokenizer.LEFT_PAREN,"'(' position");
    nextToken();

    double x,y;
    expect(Tokenizer.NUMBER,"position x-coordinate");
    x=Double.parseDouble(scanner_.matchedText());
    nextToken();

    expect(Tokenizer.COMMA,"',' comma separating coordinates");
    nextToken();

    expect(Tokenizer.NUMBER,"position y-coordinate");
    y=Double.parseDouble(scanner_.matchedText());
    nextToken();

    expect(Tokenizer.RIGHT_PAREN,"')' position");
    nextToken();

    return new double[] { x,y };
  }

  /** parse weight */
  protected Double weight(int level) {
    verbose(level,"<weight>");

    if (lookahead()!=Tokenizer.LEFT_BRACE)
      return null;

    nextToken();
    expect(Tokenizer.NUMBER,"number (weight)");

    double w=Double.parseDouble(scanner_.matchedText());
    nextToken();

    expect(Tokenizer.RIGHT_BRACE,"')'");
    nextToken();

    return w;
  }


  /** test and example for usage */
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    GraphAM<SimpleNode,SimpleEdge> g=
      new GraphAM<SimpleNode,SimpleEdge>
      (new SimpleNode(),new SimpleEdge(),false);

    String input=(args.length>0) ?
        Sys.readFile(new File(args[0])) : "a@(1,1) -- [1] b b -- c c -- a";

    System.out.println("input = '"+input+"'");

    GraphParser p=
      new GraphParser((AbstractGraph<AbstractNode,AbstractEdge>) (Object) g);
    p.setVerbose(true);
    p.parse(input);

    System.out.println(g);
    System.out.println(g.toDot());

    DotViewer.displayWindow(g,"graph").setExitOnClose();
  }
}
