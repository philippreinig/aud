package examprep;

import java.util.Iterator;

public class Ring<T> implements Iterable<Ring.RNode<T>> {
    RNode<T> head;
    RNode<T> tail;

    public void remove(final RNode<T> element) {
        if (this.isEmpty()) {
            return;
        } else {
            RNode<T> next = this.head;
            if (next == element) {
                return next;
            }
            while ( next.next != this.head;){

            }
        }


    }

    public void remove(final int i) {

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

    }

    public void insertBefore(final RNode<T> ref, final T data) {

    }

    public RNode<T> find(final T data) {
        final RNode<T> next = this.head;
        if (next != null) {
            while (next.next != this.head) {
                if (next.data.equals(data)) {
                    return next;
                }
            }
        }
        return null;
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
