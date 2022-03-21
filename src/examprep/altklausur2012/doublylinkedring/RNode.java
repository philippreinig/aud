package examprep.altklausur2012.doublylinkedring;

public class RNode<T> {
    private final T data;
    private RNode<T> next;
    private RNode<T> prev;

    RNode(final T data) {
        this(data, null, null);
    }

    RNode(final T data, final RNode<T> prev, final RNode<T> next) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public T getData(){
        return this.data;
    }

    public RNode<T> getNext(){
        return this.next;
    }

    public RNode<T> getPrev(){
        return this.prev;
    }

    public void setPrev(RNode<T> prev){
        this.prev = prev;
    }

    public void setNext(RNode<T> next){
        this.next = next;
    }

    public String toStringShort() {
        return "(" + this.data.toString() + ")";
    }

    @Override
    public String toString() {
        final String prevStr = this.prev != null ? this.prev.data.toString() : "null";
        final String nextStr = this.next != null ? this.next.data.toString() : "null";
        return "(" + prevStr + ")" + " <-> " + "(" + this.data.toString() + ")" + " <-> " + "(" + nextStr + ")";
    }
}