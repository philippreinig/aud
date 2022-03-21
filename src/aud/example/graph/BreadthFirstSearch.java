package aud.example.graph;

import aud.Queue;

/**
 * implements BFS
 */
public class BreadthFirstSearch extends Traversal {

    public BreadthFirstSearch(final MyGraph g) {
        super(g);
    }

    @Override
    public String name() {
        return "BFS";
    }

    ;

    @Override
    public void start(final MyNode s0) {
      this.initialize(); // reset all node attributes

        int count = 0;
        final Queue<MyNode> open = new Queue<MyNode>();
        s0.ord = count++;
        s0.d = 0.0;

      this.showMark(s0);

        open.enqueue(s0);

        while (!open.isEmpty()) {
            if (this.verbose > 0)
                System.out.println("open=" + open.toString());

            final MyNode s = open.dequeue();

          this.showVisit(s);

            for (final MyEdge e : this.g_.getOutEdges(s)) {

                if (this.verbose > 0)
                    System.err.println(e);

                MyNode t = (MyNode) e.destination();
                if (t == s)
                    t = (MyNode) e.source(); // undirected graph

                if (t.ord < 0) { // <=> not marked
                    t.ord = count++;
                    t.p = s;
                    t.d = s.d + (e.hasWeight() ? e.getWeight() : 1.0);

                  this.showMark(t);

                    open.enqueue(t);
                }
            }
        }
    }

}
