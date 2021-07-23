package examprep;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Ring<T> implements Iterable<Ring.RNode<T>> {
    private final boolean check = false;
    private RNode<T> head;
    private RNode<T> tail;

    public void remove(final RNode<T> node) {
        if (node.next == null || node.prev == null) {
            throw new IllegalArgumentException(node + " contains invalid references to prev or next -> cant perform remove operation");
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
            newNode.next = this.head;
            newNode.prev = this.tail;
            this.head.prev = newNode;
            this.tail.next = newNode;
            this.head = newNode;
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
        if (ref == null) {

        }

    }

    public void insertBefore(final RNode<T> ref, final T data) {

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

    @Override
    public Iterator<RNode<T>> iterator() {
        return new Iterator<>() {
            RNode<T> next = Ring.this.head;

            @Override
            public boolean hasNext() {
                return this.next != null && this.next.next != Ring.this.head;
            }

            @Override
            public RNode<T> next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return (this.next = this.next.next).prev;
            }
        };
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
            return this.prev.data.toString() + " <-> " + this.data.toString() + " <-> " + this.next.data.toString();
        }
    }

}
