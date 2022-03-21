package examprep.altklausur2012.doublylinkedring;

public class RIntNode extends RNode<Integer> {
    RIntNode(final Integer data) {
        super(data);
    }

    @Override
    public RIntNode getNext() {
        return (RIntNode) super.getNext();
    }

    @Override
    public RIntNode getPrev() {
        return (RIntNode) super.getPrev();
    }


}
