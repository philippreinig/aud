package aud.example.graph;

import aud.graph.AbstractEdge;
import aud.graph.AbstractGraph;
import aud.graph.AbstractNode;
import aud.graph.GraphAM;
import aud.util.GraphvizDecorator;
import aud.util.Sys;

import java.io.File;

/**
 * graph based on {@link aud.graph.GraphAM}
 *
 * @see MyNode
 * @see MyEdge
 */
public class MyGraph extends GraphAM<MyNode, MyEdge> {

    MyDecorator decorator_ = new MyDecorator(this);

    /**
     * create empty graph
     */
    public MyGraph(final boolean directed) {
        super(new MyNode(), new MyEdge(), directed);
    }

    /**
     * read graph from file using {@link GraphParser}
     */
    public MyGraph(final boolean directed, final File filename) {
        this(directed);
        final String text = Sys.readFile(filename);
        new GraphParser(this.getAbstractGraph()).parse(text);
    }

    /**
     * view this graph as an {@link AbstractGraph}
     */
    @SuppressWarnings("unchecked")
    public AbstractGraph<AbstractNode, AbstractEdge> getAbstractGraph() {
        // require weird cast -- java generics suck!
        return (AbstractGraph<AbstractNode, AbstractEdge>) (Object) this;
    }


    @Override
    public GraphvizDecorator getDecorator() {
        return this.decorator_;
    }
}
