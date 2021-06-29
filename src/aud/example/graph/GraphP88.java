package aud.example.graph;

/** undirected (weighted or unweighted )example graph
    (Sedgewick, Algorithms in Java. Part 5: Graph Algorithms.
     3rd ed. p88)
 */
public class GraphP88 extends MyGraph {
  public GraphP88(boolean weighted) {
    super(false);
    MyNode n0=(MyNode) addNode();
    MyNode n1=(MyNode) addNode();
    MyNode n2=(MyNode) addNode();
    MyNode n3=(MyNode) addNode();
    MyNode n4=(MyNode) addNode();
    MyNode n5=(MyNode) addNode();
    MyNode n6=(MyNode) addNode();
    MyNode n7=(MyNode) addNode();

    n0.setLabel("A");
    n1.setLabel("B");
    n2.setLabel("C");
    n3.setLabel("D");
    n4.setLabel("E");
    n5.setLabel("F");
    n6.setLabel("G");
    n7.setLabel("H");

    n0.setPosition(0.0, 0.0);
    n1.setPosition(1.0,-2.5);
    n2.setPosition(2.0, 0.0);
    n3.setPosition(1.5,-3.75);
    n4.setPosition(3.0,-5.0);
    n5.setPosition(0.0,-5.0);
    n6.setPosition(4.5,-2.0);
    n7.setPosition(3.0,-2.5);

    if (weighted) {
      addEdge(n0,n2).setWeight(20);
      addEdge(n0,n5).setWeight(50);
      addEdge(n0,n7).setWeight(39);
      addEdge(n1,n7).setWeight(20);
      addEdge(n2,n6).setWeight(33);
      addEdge(n3,n4).setWeight(27);
      addEdge(n3,n5).setWeight(27);
      addEdge(n4,n5).setWeight(30);
      addEdge(n4,n7).setWeight(25);
      addEdge(n4,n6).setWeight(31);
    }
    else {
      addEdge(n0,n2);
      addEdge(n0,n5);
      addEdge(n0,n7);
      addEdge(n1,n7);
      addEdge(n2,n6);
      addEdge(n3,n4);
      addEdge(n3,n5);
      addEdge(n4,n5);
      addEdge(n4,n7);
      addEdge(n4,n6);
    }
  }
  public GraphP88() {
    this(false);
  }

  public static void main(String[] args) {
    System.out.println(new GraphP88());
  }
}
