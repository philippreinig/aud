package aud.graph;

/**
 * plain simple node
 */
public class SimpleNode extends AbstractNode {

    String label_ = null;
    double[] position_ = null;

    @Override
    public SimpleNode create() {
        return new SimpleNode();
    }

    @Override
    public String getLabel() {
        return this.label_ == null ? super.getLabel() : this.label_;
    }

    /**
     * set label (default label if {@code label==null})
     */
    @Override
    public void setLabel(final String label) {
      this.label_ = label;
    }

    @Override
    public void setPosition(final double x, final double y) {
      this.position_ = new double[]{x, y};
    }

    /**
     * helper for drawing the graph: return {x,y} as array or {@code null}
     */
    @Override
    public double[] getPosition() {
        return this.position_;
    }

}
