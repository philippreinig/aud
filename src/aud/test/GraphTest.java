package aud.test;

import aud.Vector;
import aud.graph.*;

import java.util.TreeSet;

import org.junit.*;
import static org.junit.Assert.*;

public class GraphTest {

  public void testDirectedGraph
    (AbstractGraph<SimpleNode,SimpleEdge> g) {

    assertTrue(g.isDirected());

    SimpleNode n0=g.addNode(); assertNotNull(n0);
    SimpleNode n1=g.addNode(); assertNotNull(n1);
    SimpleNode n2=g.addNode(); assertNotNull(n2);

    assertSame(n0.graph(),g);
    assertSame(n1.graph(),g);
    assertSame(n2.graph(),g);

    SimpleEdge e0=g.addEdge(n0,n1); assertNotNull(e0);
    SimpleEdge e1=g.addEdge(n1,n0); assertNotNull(e1);

    SimpleEdge e2=g.addEdge(n0,n2); assertNotNull(e2);
    SimpleEdge e3=g.addEdge(n2,n1); assertNotNull(e3);

    assertSame(e0.graph(),g);
    assertSame(e1.graph(),g);
    assertSame(e2.graph(),g);
    assertSame(e3.graph(),g);

    assertSame(e0.source(),n0);
    assertSame(e1.source(),n1);
    assertSame(e2.source(),n0);
    assertSame(e3.source(),n2);

    assertSame(e0.destination(),n1);
    assertSame(e1.destination(),n0);
    assertSame(e2.destination(),n2);
    assertSame(e3.destination(),n1);

    assertSame(g.getEdge(n0,n1),e0);
    assertSame(g.getEdge(n1,n0),e1);
    assertSame(g.getEdge(n0,n2),e2);
    assertSame(g.getEdge(n2,n1),e3);

    //System.err.println(g);//

    TreeSet<SimpleNode> nodes=new TreeSet<SimpleNode>();
    for (SimpleNode node : g) {
      boolean b=nodes.add(node);
      assertTrue(b);
    }
    assertSame(nodes.size(),3);
    assertTrue(nodes.contains(n0));
    assertTrue(nodes.contains(n1));
    assertTrue(nodes.contains(n2));

    TreeSet<SimpleEdge> edges=new TreeSet<SimpleEdge>();
    for (SimpleEdge edge : g.edges()) {
      boolean b=edges.add(edge);
      assertTrue(b);
    }
    assertSame(edges.size(),4);
    assertTrue(edges.contains(e0));
    assertTrue(edges.contains(e1));
    assertTrue(edges.contains(e2));
    assertTrue(edges.contains(e3));

    Vector<SimpleEdge> out=g.getOutEdges(n0);
    assertSame(out.size(),g.getOutDegree(n0));
    assertSame(out.size(),2);
    assertTrue(out.at(0)==e0 || out.at(1)==e2);
    assertTrue(out.at(1)==e0 || out.at(1)==e2);

    Vector<SimpleEdge> in=g.getInEdges(n0);
    assertSame(in.size(),g.getInDegree(n0));
    assertSame(in.size(),1);
    assertSame(in.at(0),e1);

    assertSame(g.getDegree(n0),in.size()+out.size());

    //

    g.removeEdge(e0);
    edges.remove(e0);

    //System.err.println(g);//

    assertNull(g.getEdge(n0,n1));
    out=g.getOutEdges(n0);
    assertSame(out.size(),g.getOutDegree(n0));
    assertSame(out.size(),1);
    assertSame(out.at(0),e2);

    in=g.getInEdges(n1);
    assertSame(in.size(),g.getInDegree(n1));
    assertSame(in.size(),1);
    assertSame(in.at(0),e3);

    assertSame(g.getDegree(n0),in.size()+out.size());

    //

    g.removeNode(n2);
    nodes.remove(n2);
    edges.remove(e2);
    edges.remove(e3);

    //System.err.println(g);//

    assertSame(g.getNumNodes(),2);
    assertTrue(g.getSomeNode()==n0 || g.getSomeNode()==n1);

    for (SimpleNode node : g)
      assertTrue(nodes.contains(node));

    {
      int n=0;
      for (SimpleEdge edge : g.edges()) {
	assertTrue(edges.contains(edge));
	++n;
      }
      assert(n==1);
    }
  }

  public void testUndirectedGraph
    (AbstractGraph<SimpleNode,SimpleEdge> g) {

    assertFalse(g.isDirected());

    SimpleNode n0=g.addNode(); assertNotNull(n0);
    SimpleNode n1=g.addNode(); assertNotNull(n1);
    SimpleNode n2=g.addNode(); assertNotNull(n2);

    assertSame(n0.graph(),g);
    assertSame(n1.graph(),g);
    assertSame(n2.graph(),g);

    SimpleEdge e0=g.addEdge(n0,n1); assertNotNull(e0);
    SimpleEdge e1=g.addEdge(n2,n1); assertNotNull(e1);

    assertSame(e0.graph(),g);
    assertSame(e1.graph(),g);

    assertSame(g.getEdge(n0,n1),e0);
    assertSame(g.getEdge(n1,n0),e0);
    assertSame(g.getEdge(n1,n2),e1);
    assertSame(g.getEdge(n2,n1),e1);

    //System.err.println(g);//

    TreeSet<SimpleNode> nodes=new TreeSet<SimpleNode>();
    for (SimpleNode node : g) {
      boolean b=nodes.add(node);
      assertTrue(b);
    }
    assertSame(nodes.size(),3);
    assertTrue(nodes.contains(n0));
    assertTrue(nodes.contains(n1));
    assertTrue(nodes.contains(n2));

    TreeSet<SimpleEdge> edges=new TreeSet<SimpleEdge>();
    for (SimpleEdge edge : g.edges()) {
      boolean b=edges.add(edge);
      assertTrue(b);
    }
    assertSame(edges.size(),2);
    assertTrue(edges.contains(e0));
    assertTrue(edges.contains(e1));

    Vector<SimpleEdge> out=g.getOutEdges(n0);
    assertSame(out.size(),g.getOutDegree(n0));
    assertSame(out.size(),1);
    assertTrue(out.at(0)==e0);

    Vector<SimpleEdge> in=g.getInEdges(n0);
    assertSame(in.size(),g.getInDegree(n0));
    assertSame(in.size(),1);
    assertSame(in.at(0),e0);

    assertSame(g.getDegree(n0),in.size());

    //

    g.removeEdge(e0);
    edges.remove(e0);

    //System.err.println(g);//

    assertNull(g.getEdge(n0,n1));
    assertNull(g.getEdge(n1,n0));
    out=g.getOutEdges(n0);
    assertSame(out.size(),g.getDegree(n0));
    assertSame(out.size(),0);

    in=g.getInEdges(n0);
    assertSame(in.size(),g.getDegree(n0));
    assertSame(in.size(),0);


    out=g.getOutEdges(n1);
    assertSame(out.size(),g.getDegree(n1));
    assertSame(out.size(),1);
    assertSame(out.at(0),e1);

    in=g.getInEdges(n1);
    assertSame(in.size(),g.getDegree(n1));
    assertSame(in.size(),1);
    assertSame(in.at(0),e1);

    //

    g.removeNode(n2);
    nodes.remove(n2);

    //System.err.println(g);//

    assertSame(g.getNumNodes(),2);
    assertTrue(g.getSomeNode()==n0 || g.getSomeNode()==n1);

    for (SimpleNode node : g)
      assertTrue(nodes.contains(node));

    {
      int n=0;
      for (@SuppressWarnings("unused") SimpleEdge edge : g.edges())
        ++n;
      assert(n==0);
    }
  }

  @Test
  public void testGraph() {
    testDirectedGraph(new GraphAM<SimpleNode,SimpleEdge>
		      (new SimpleNode(),new SimpleEdge(),true));
    testUndirectedGraph(new GraphAM<SimpleNode,SimpleEdge>
			(new SimpleNode(),new SimpleEdge(),false));
  }


  @Test(expected=IllegalArgumentException.class)
  public void testOther() {
    GraphAM<SimpleNode,SimpleEdge> ga=new GraphAM<SimpleNode,SimpleEdge>
      (new SimpleNode(),new SimpleEdge(),true);
    GraphAM<SimpleNode,SimpleEdge> gb=new GraphAM<SimpleNode,SimpleEdge>
      (new SimpleNode(),new SimpleEdge(),true);
    SimpleNode na1=ga.addNode();
    SimpleNode na2=ga.addNode();
    gb.addEdge(na1,na2);
  }

  @Test(expected=RuntimeException.class)
  public void testDouble() {
    GraphAM<SimpleNode,SimpleEdge> g=new GraphAM<SimpleNode,SimpleEdge>
      (new SimpleNode(),new SimpleEdge(),false);
    SimpleNode n1=g.addNode();
    SimpleNode n2=g.addNode();
    g.addEdge(n1,n2);
    g.addEdge(n2,n1);
  }

  @Test(expected=RuntimeException.class)
  public void testDouble2() {
    GraphAM<SimpleNode,SimpleEdge> g=new GraphAM<SimpleNode,SimpleEdge>
      (new SimpleNode(),new SimpleEdge(),true);
    SimpleNode n1=g.addNode();
    SimpleNode n2=g.addNode();
    g.addEdge(n1,n2);
    g.addEdge(n1,n2);
  }

  public static void main(String args[]) {
    org.junit.runner.JUnitCore.main("aud.test.GraphTest");
  }
}
