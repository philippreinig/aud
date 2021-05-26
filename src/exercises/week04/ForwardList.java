package exercises.week04;

import aud.Stack;

import java.util.NoSuchElementException;

/**
 * ForwardList represents a list with single linked nodes, that do have
 * a next-pointer.
 */
public class ForwardList<T> implements Iterable<T> {
    public Node head_ = null;

    public ForwardList() {
    }

    public static void main(String[] args) {
        ForwardList<Integer> fw = new ForwardList<>();
        fw.push_front(1);
        fw.push_front(2);
        fw.push_front(3);
        fw.push_front(4);
        fw.push_front(5);

        for (Integer element : fw) System.out.println(element);
    }

    @Override
    public String toString() {
        if (this.is_empty()) return "[]";
        StringBuilder rv = new StringBuilder("[");
        Node node = this.head_;
        do {
            rv.append(node.data_.toString());
            if (node.next_ != this.head_) rv.append(",");
            node = node.next_;
        } while (node != null);
        rv.append("]");
        return rv.toString();
    }

    public void push_front(T obj) {
        this.head_ = new Node(obj, this.head_);
    }

    public boolean is_empty() {
        return this.head_ == null;
    }

    public void backTraverse() {
        while (this.iterator().hasNext()) System.out.println(this.iterator().next().toString());
    }

    @Override
    public BackIterator iterator() {
        return new BackIterator();
    }

    private Node getLastNode(Node lastNode) {
        Node traverser = head_;
        while (traverser.next_ != null && traverser.next_ != lastNode) traverser = traverser.next_;
        return traverser;
    }

    public class Node {
        public T data_;
        public Node next_;

        public Node(T data, Node next) {
            this.data_ = data;
            this.next_ = next;
        }
    }

    private class BackIterator implements java.util.Iterator<T> {
        Stack<T> stack = new Stack<>();

        private BackIterator() {
            Node next = head_;
            while (next != null) {
                stack.push(next.data_);
                next = next.next_;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.is_empty();
        }

        @Override
        public T next() {
            if (stack.is_empty()) throw new NoSuchElementException();
            return stack.pop();
        }
    }
}