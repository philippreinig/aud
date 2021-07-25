package examprep;

import aud.util.DotViewer;
import aud.util.Graphvizable;
import aud.util.SingleStepper;

import javax.swing.*;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Ring<T> implements Iterable<Ring.RNode<T>>, Graphvizable {
    private static final boolean CHECK = false;
    private static final boolean VISUAL_DEBUG = false;
    private SingleStepper ss;
    private DotViewer dv;
    private RNode<T> head;
    private RNode<T> tail;

    Ring() {
        if (Ring.VISUAL_DEBUG) {

            final JFrame dv_frame = new JFrame();
            this.dv = new DotViewer(dv_frame);
            dv_frame.pack();
            dv_frame.setVisible(true);
            this.ss = new SingleStepper("Single Stepper");
        }
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
            if (Ring.VISUAL_DEBUG) {
                this.show();
            }
            this.tail.next = newNode;
            if (Ring.VISUAL_DEBUG) {
                this.show();
            }
            newNode.next = this.head;
            if (Ring.VISUAL_DEBUG) {
                this.show();
            }
            newNode.prev = this.tail;
            if (Ring.VISUAL_DEBUG) {
                this.show();
            }
            this.head = newNode;
            if (Ring.VISUAL_DEBUG) {
                this.show();
            }

        }

    }

    public void insertBack(final T data) {
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
        throw new NoSuchElementException();

//        final RNode<T> next = this.head;
//        if (next != null) {
//            while (next.next != this.head) {
//                if (next.data.equals(data)) {
//                    return next;
//                }
//            }
//        }
//        return null;
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
                    hasNext = this.next != Ring.this.tail || this.tailReached;
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
        String dotStr;
        if (this.isEmpty()) {
            dotStr = "digraph Ring{\"null\"}";
        } else {
            dotStr = "digraph Ring {\n";
            for (final RNode<T> node : this) {
                final String prvStr = node.prev.data.toString();
                final String nxtStr = node.next.data.toString();
                final String nodeStr = node.data.toString();
                dotStr += "\"" + nodeStr + "\"" + ARROW_PREV + "\"" + prvStr + "\"" + LABEL_PREV + ";\n"
                        + "\"" + nodeStr + "\"" + ARROW_NEXT + "\"" + nxtStr + "\"" + LABEL_NEXT + ";\n";
            }
            dotStr += "}";
        }
        System.out.println(dotStr);
        return dotStr;
    }

    public void show() {
        if (Ring.VISUAL_DEBUG) {
            this.dv.display(this);
            this.ss.halt();
        }
    }

    @Override
    public String toString() {
        final StringBuilder ringStr = new StringBuilder();
        for (final RNode<T> node : this) {
            ringStr.append(node.toString()).append("\n");
        }
        return ringStr.toString();
    }

    public static class RNode<T> {
        T data;
        RNode<T> next;
        RNode<T> prev;

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

        @Override
        public String toString() {
            final String prevStr = this.prev != null ? this.prev.data.toString() : "null";
            final String nextStr = this.next != null ? this.next.data.toString() : "null";
            return "(" + prevStr + ")" + " <-> " + "(" + this.data.toString() + ")" + " <-> " + "(" + nextStr + ")";
        }
    }

}
