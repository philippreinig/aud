package exercises.week10;

import aud.example.graph.*;
import aud.util.DotViewer;
import aud.util.SimpleDecorator;
import aud.util.SingleStepper;

import javax.swing.*;

public class DfsAndBfsTest {
    static class SingleStepperWithDotViewer extends SingleStepper {

        public MyGraph graph = null;
        protected DotViewer viewer_ = DotViewer.displayWindow((String) null, "Graph");

        public SingleStepperWithDotViewer() {
            super("Graph");
        }


        @Override
        protected void onHalt() {
            if (this.graph != null) {
                this.viewer_.display(this.graph);
            }
        }

    }

    public static void main(final String[] args) {
//        Nod
        final MyGraph graph = new MyGraph(false);
        final int amountNodes = 8;
        final MyNode[] myNodes = new MyNode[amountNodes];
        for (int i = 0; i < amountNodes; i++) {
            myNodes[i] = graph.addNode();
            myNodes[i].setLabel(String.valueOf(i + 1));
        }
        graph.addEdge(myNodes[0], myNodes[1]);
        graph.addEdge(myNodes[0], myNodes[2]);
        graph.addEdge(myNodes[0], myNodes[5]);
        graph.addEdge(myNodes[0], myNodes[6]);
        graph.addEdge(myNodes[0], myNodes[7]);
        graph.addEdge(myNodes[1], myNodes[7]);
        graph.addEdge(myNodes[2], myNodes[7]);
        graph.addEdge(myNodes[3], myNodes[4]);
        graph.addEdge(myNodes[3], myNodes[5]);
        graph.addEdge(myNodes[4], myNodes[5]);
        graph.addEdge(myNodes[4], myNodes[6]);
        graph.addEdge(myNodes[4], myNodes[7]);
        DotViewer.displayWindow(graph.toDot(), null);

        final JFrame dlg = new JFrame();
        final String[] options = new String[2];
        options[0] = new String("Breadth-Search");
        options[1] = new String("Depth-Search");
        final int res = JOptionPane.showOptionDialog(dlg.getContentPane(),
                "Select the desired algorithm:", "Graph Search", 0,
                JOptionPane.QUESTION_MESSAGE, null, options, null);

        Traversal algorithm = null;
        if (res == 0) {
            algorithm = new BreadthFirstSearch(graph);
        } else if (res == 1) {
            algorithm = new DepthFirstSearch(graph);
        }
        if (algorithm == null) {
            return;
        }
        final JFrame frame = new JFrame("Graph");
        frame.setSize(600, 400);

        final SingleStepperWithDotViewer stepper = new SingleStepperWithDotViewer();
        stepper.graph = graph;

        algorithm.singlestepper = stepper;
        algorithm.verbose = 0;

        // Startknoten 8
        stepper.halt(algorithm.name() + " from 8");
        algorithm.start(myNodes[7]);

        ((SimpleDecorator) graph.getDecorator()).highlightNode(null);
        ((SimpleDecorator) graph.getDecorator()).highlightEdge(null);

        stepper.halt("DONE");

        System.out.println(graph);

        System.exit(0);

    }
}
