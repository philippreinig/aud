package aud.example.graph;

import aud.util.GraphvizDecorable;
import aud.util.SimpleDecorator;

public class MyDecorator extends SimpleDecorator {

    MyGraph g;

    public MyDecorator(final MyGraph g) {
        this.g = g;
    }

    @Override
    public String getNodeDecoration(final GraphvizDecorable object) {
        String decoration = super.getNodeDecoration(object);
        if ((decoration == null || !decoration.contains("fillcolor=")) &&
                ((MyNode) object).color != null) {
            decoration = (decoration == null ? "" : decoration + ",") +
                    "style=filled,fillcolor=" + ((MyNode) object).color;
        }
        return decoration;
    }

    @Override
    public String getEdgeDecoration(final GraphvizDecorable object) {
        String decoration = super.getEdgeDecoration(object);
        if ((decoration == null || !decoration.contains("color=")) &&
                ((MyEdge) object).color != null) {
            decoration = (decoration == null ? "" : decoration + ",") +
                    "color=" + ((MyEdge) object).color;
        }
        if (((MyEdge) object).penwidth >= 0.0) {
            decoration = (decoration == null ? "" : decoration + ",") +
                    "penwidth=" + ((MyEdge) object).penwidth;
        }
        return decoration;
    }
}
