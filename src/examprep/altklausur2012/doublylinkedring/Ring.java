package examprep.altklausur2012.doublylinkedring;

import aud.util.Graphvizable;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Ring<T> implements Iterable<RNode<T>>, Graphvizable {
    private static final boolean CHECK = false;
    private RNode<T> head;
    private RNode<T> tail;

    public void remove(final RNode<T> node) {
        if (node.getNext() == null || node.getPrev() == null) {
            throw new IllegalArgumentException(node + " contains invalid references to prev or next -> cant perform remove operation");
        }
        if (Ring.CHECK) {
            this.checkIfNodeContainedInRing(node);
        }
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        node.setPrev(null);
        node.setNext(null);
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
            newNode.setNext(newNode);
            newNode.setPrev(newNode);
        } else {
            this.getHead().setPrev(newNode);
            this.getTail().setNext(newNode);
            newNode.setNext(this.head);
            newNode.setPrev(this.tail);
            this.head = newNode;
        }
    }

    public RNode<T> getNthNode(final int n) {
        final Iterator<RNode<T>> iter = this.iterator();
        RNode<T> curr = iter.next();
        for (int i = 0; i < n; ++i) {
            curr = iter.next();
        }
        return curr;
    }


    public RNode<T> insertBack(final T data) {
        final RNode<T> newNode = new RNode<>(data);
        if (this.isEmpty()) {
            assert (this.tail == null);
            this.insertFront(data);
        } else {
            newNode.setNext(this.head);
            newNode.setPrev(this.tail);
            this.getHead().setPrev(newNode);
            this.getTail().setNext(newNode);
            this.setTail(newNode);
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

        newNode.setPrev(ref);
        newNode.setNext(ref.getNext());

        ref.setNext(newNode);
        newNode.getNext().setPrev(newNode);

        if (ref == this.tail) this.tail = newNode;
    }

    public void insertBefore(final RNode<T> ref, final T data) {
        if (Ring.CHECK) {
            this.checkIfNodeContainedInRing(ref);
        }
        if (this.isEmpty()) {
            throw new IllegalArgumentException(ref + " is not an element of " + this);
        }

        final RNode<T> newNode = new RNode<>(data);
        newNode.setPrev(ref.getPrev());
        newNode.setNext(ref);
        ref.getPrev().setNext(newNode);
        ref.setPrev(newNode);

        if (ref == this.head) this.head = newNode;
    }

    public RNode<T> find(final T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        for (final RNode<T> node : this) {
            if (node.getData().equals(data)) {
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

    public RNode<T> getHead() {
        return this.head;
    }

    public void setHead(final RNode<T> head) {
        this.head = head;
    }

    public RNode<T> getTail() {
        return this.tail;
    }

    public void setTail(final RNode<T> tail) {
        this.tail = tail;
    }


    @Override
    public Iterator<RNode<T>> iterator() {
        return new Iterator<>() {
            boolean tailReached = false;
            RNode<T> nextNodeForIterator = Ring.this.head;

            @Override
            public boolean hasNext() {
                final boolean hasNext;
                if (Ring.this.isEmpty()) {
                    hasNext = false;
                } else if (Ring.this.head == Ring.this.tail) {
                    hasNext = !this.tailReached;

                } else {
                    hasNext = !this.tailReached || this.nextNodeForIterator == Ring.this.tail;
                }
                return hasNext;
            }

            @Override
            public RNode<T> next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.nextNodeForIterator = this.nextNodeForIterator.getNext();
                if (this.nextNodeForIterator == Ring.this.tail) {
                    this.tailReached = true;
                }
                return this.nextNodeForIterator.getPrev();
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
                final String prvStr = node.getPrev().getData().toString();
                final String nxtStr = node.getNext().getData().toString();
                final String nodeStr = node.getData().toString();
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
            ringStr.append(node.getData().toString()).append(" | ");
        }
        return ringStr.toString();
    }

}
