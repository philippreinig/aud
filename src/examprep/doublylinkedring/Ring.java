package examprep.doublylinkedring;

import aud.util.Graphvizable;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Ring<T> implements Iterable<Ring.RNode<T>>, Graphvizable {
    private static final boolean CHECK = false;
    private RNode<T> head;
    private RNode<T> tail;

    public Ring() {
    }

    public void remove(final RNode<T> node) {
        if (node.next == null || node.prev == null) {
            throw new IllegalArgumentException(node + " contains invalid references to prev or next -> cant perform remove operation");
        }
        if (Ring.CHECK) {
            this.checkIfNodeContainedInRing(node);
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
        System.err.println("remove:" + this + " does not contain " + node);
    }

    public void remove(final int x) {
        try {
            final var iter = this.iterator();
            for (int i = 0; i < x; ++i) {
                iter.next();
            }
            this.remove(iter.next());
        } catch (final NoSuchElementException nsee) {
            throw new IndexOutOfBoundsException(x);
        }


    }

    public void insertFront(final T data) {
        final RNode<T> newNode = new RNode<>(data);
        if (this.isEmpty()) {
            assert (this.tail == null);
            this.head = newNode;
            this.tail = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            this.head.prev = newNode;
            this.tail.next = newNode;
            newNode.next = this.head;
            newNode.prev = this.tail;
            this.head = newNode;
        }
    }

    public RNode<T> getNode(final int x) {
        final Iterator<RNode<T>> iter = this.iterator();
        RNode<T> curr = iter.next();
        for (int i = 1; i <= x; ++i) {
            curr = iter.next();
        }
        return curr;
    }

    public T getData(final int x) {
        return this.getNode(x).data;
    }

    public RNode<T> insertBack(final T data) {
        final RNode<T> newNode = new RNode<>(data);
        if (this.isEmpty()) {
            assert (this.tail == null);
            this.insertFront(data);
        } else {
            newNode.next = this.head;
            newNode.prev = this.tail;
            this.head.prev = newNode;
            this.tail.next = newNode;
            this.tail = newNode;
        }
        return newNode;
    }

    public void insertAfter(final RNode<T> ref, final T data) {
        if (Ring.CHECK) {
            this.checkIfNodeContainedInRing(ref);
        }
        if (this.isEmpty()) {
            throw new IllegalArgumentException(ref + " is not an element of " + this);
        }
        final RNode<T> newNode = new RNode<>(data);

        newNode.prev = ref;
        newNode.next = ref.next;

        ref.next = newNode;
        newNode.next.prev = newNode;
    }

    public void insertBefore(final RNode<T> ref, final T data) {
        if (Ring.CHECK) {
            this.checkIfNodeContainedInRing(ref);
        }
        if (this.isEmpty()) {
            throw new IllegalArgumentException(ref + " is not an element of " + this);
        }

        final RNode<T> newNode = new RNode<>(data);
        newNode.prev = ref.prev;
        newNode.next = ref;
        ref.prev.next = newNode;
        ref.prev = newNode;
    }

    public RNode<T> find(final T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        for (final RNode<T> node : this) {
            if (node.data.equals(data)) {
                return node;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    private void checkIfNodeContainedInRing(final RNode<T> node) {
        for (final RNode<T> n : this) {
            if (n == node) {
                return;
            }
        }
        throw new NoSuchElementException(node + " is not contained in " + this);
    }

    @Override
    public Iterator<RNode<T>> iterator() {
        return new Iterator<>() {
            boolean tailReached = false;
            RNode<T> next = Ring.this.head;

            @Override
            public boolean hasNext() {
                final boolean hasNext;
                if (Ring.this.isEmpty()) {
                    hasNext = false;
                } else if (Ring.this.head == Ring.this.tail) {
                    hasNext = !this.tailReached;

                } else {
                    hasNext = !this.tailReached || this.next == Ring.this.tail;
                }
                return hasNext;
            }

            @Override
            public RNode<T> next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.next = this.next.next;
                if (this.next == Ring.this.tail) {
                    this.tailReached = true;
                }
                return this.next.prev;
            }
        };
    }

    @Override
    public String toDot() {
        final String ARROW_PREV = " -> ";
        final String ARROW_NEXT = " -> ";
        final String LABEL_PREV = " [label=\"prev\"]";
        final String LABEL_NEXT = " [label=\"next\"]";
        final StringBuilder dotStr;
        if (this.isEmpty()) {
            dotStr = new StringBuilder("digraph Ring{\"null\"}");
        } else {
            dotStr = new StringBuilder("digraph Ring {\n");
            for (final RNode<T> node : this) {
                final String prvStr = node.prev.data.toString();
                final String nxtStr = node.next.data.toString();
                final String nodeStr = node.data.toString();
                dotStr.append("\"").append(nodeStr).append("\"").append(ARROW_PREV)
                        .append("\"").append(prvStr).append("\"").append(LABEL_PREV)
                        .append(";\n").append("\"").append(nodeStr).append("\"").append(ARROW_NEXT)
                        .append("\"").append(nxtStr).append("\"").append(LABEL_NEXT).append(";\n");
            }
            dotStr.append("}");
        }
        System.out.println(dotStr);
        return dotStr.toString();
    }

    @Override
    public String toString() {
        final StringBuilder ringStr = new StringBuilder();
        ringStr.append(" | ");
        for (final RNode<T> node : this) {
            ringStr.append(node.data.toString()).append(" | ");
        }
        return ringStr.toString();
    }

    public static class RNode<T> {
        public T data;
        public RNode<T> next;
        public RNode<T> prev;

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

}
