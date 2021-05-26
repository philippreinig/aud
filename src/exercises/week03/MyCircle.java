package exercises.week03;

import java.util.NoSuchElementException;

public class MyCircle<T> {
    protected Node head_;

    public MyCircle() {
        this.head_ = null;
    }

    public static void main(String[] args) {
        // -------- empty --------------------------------------
        System.out.println("============= Testing empty =============");
        MyCircle<Integer> myc1 = new MyCircle<>();
        System.out.println("should be empty: " + myc1.empty());
        myc1.push_back(1);
        System.out.println("should not be empty: " + myc1.empty());
        // -------- push_back ---------------------------------------
        MyCircle<Integer> myc2 = new MyCircle<>();
        System.out.println("============= Testing push_back =============");
        System.out.println("should contain nothing: " + myc2);
        for (int i = 0; i < 10; i++) myc2.push_back(i);
        System.out.println("should be [0,1,2,3,4,5,6,7,8,9]: " + myc2);
        // -------- size ---------------------------------------
        System.out.println("============= Testing size =============");
        MyCircle<Integer> myc3 = new MyCircle<>();
        System.out.println("size should be 0: " + myc3.size());
        myc3.push_back(1);
        System.out.println("size should be 1: " + myc3.size());
        for (int i = 0; i < 9; i++) myc3.push_back(i);
        System.out.println("size should be 10: " + myc3.size());
        // -------- pop_front ---------------------------------------
        System.out.println("============= Testing pop_front =============");
        MyCircle<Integer> myc4 = new MyCircle<>();
        System.out.print("Should throw NoSuchElementException(): ");
        try {
            myc4.pop_front();
        } catch (NoSuchElementException exc) {
            System.out.println("NoSuchElementExcpetion caught!");

        }

        myc4.push_back(1);
        myc4.pop_front();
        System.out.println("Should be empty: " + myc4);

        for (int i = 0; i < 10; i++) myc4.push_back(i);
        System.out.println("should be [0,1,2,3,4,5,6,7,8,9]: " + myc4);
        for (int i = 0; i < 5; i++) myc4.pop_front();
        System.out.println("should be [5,6,7,8,9]: " + myc4);
    }

    public Node getHead() {
        return this.head_;
    }

    public T front() {
        return this.head_.getData();
    }

    @Override
    public String toString() {
        if (this.empty()) return "[]";
        String rv = "[";
        Node node = this.getHead();
        do {
            rv += node.getData().toString();
            if (node.next_ != this.getHead()) rv += ",";
            node = node.getNext();
        } while (node != this.getHead());
        rv += "]";
        return rv;
    }

    public int size() {
        if (this.head_ == null) return 0;
        int counter = 1;
        Node node = this.getHead();
        while (node.getNext() != this.getHead()) {
            counter++;
            node = node.getNext();
        }
        return counter;

    }

    public boolean empty() {
        return this.getHead() == null;
    }

    public void push_back(T obj) {
        if (this.empty()) {
            this.head_ = new Node(obj, null, null);
            this.getHead().setNext(this.getHead());
            this.getHead().setPrev(this.getHead());

        } else {
            Node node = this.getHead();
            while (node.getNext() != this.getHead()) node = node.getNext();
            Node newNode = new Node(obj, node, this.getHead());
            node.setNext(newNode);
            this.getHead().setPrev(newNode);
        }
    }

    public void pop_front() {
        if (this.size() <= 1) this.head_ = null;
        else {
            this.head_.getPrev().setNext(this.head_.getNext());
            this.head_.getNext().setPrev(this.head_.getPrev());
            this.head_ = this.getHead().getNext();
        }

    }

    //A Node is a single element int the ring
    class Node {
        T data_ = null;
        Node next_ = null;
        Node prev_ = null;

        Node(T obj, Node prv, Node nxt) {
            this.data_ = obj;
            this.prev_ = prv;
            this.next_ = nxt;
        }

        private Node getNext() {
            return this.next_;
        }

        private void setNext(Node next) {
            this.next_ = next;
        }

        private Node getPrev() {
            return this.prev_;
        }

        private void setPrev(Node prev) {
            this.prev_ = prev;
        }

        private T getData() {
            return this.data_;
        }

        @Override
        public String toString() {
            return "data: " + this.data_ + ", prev: " + this.prev_ + ", next: " + this.next_;
        }
    }


}
