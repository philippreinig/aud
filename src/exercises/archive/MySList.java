package exercises.archive;

/**
 * MySList represents a single linked list, which holds elements of type
 * integer.
 */
public class MySList implements Iterable<Integer> {
    private Node head = null;

    public MySList() {
    }

    public MySList(Node head) {
        this.head = head;
    }

    public void sethead(Node head) {
        this.head = head;
    }

    @Override
    public String toString() {
        if (this.empty()) return "[]";
        String rv = "[";
        Node node = this.head;
        do {
            rv += node.data;
            if (node.next != null) rv += ",";
            node = node.next;
        } while (node != null);
        rv += "]";
        return rv;
    }

    private boolean empty() {
        return this.head == null;
    }

    //----------------------------------------------------------------//
    @Override
    public Iterator iterator() {
        return new Iterator(this.head);
    }

    public void push_back(int data) {
        if (this.head == null) this.head = new Node(data, null);
        else {
            Node node = this.head;
            while (node.getNext() != null) node = node.getNext();
            node.setNext(new Node(data, null));
        }
    }

    /**
     * Node represents a single element in the list.
     */
    static class Node {
        Integer data;
        Node next;

        Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        int getData() {
            return this.data;
        }

        Node getNext() {
            return this.next;
        }

        void setNext(Node next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return String.valueOf(this.getData());
        }
    }

    /**
     * Iterator is used to traverse odd elements in a list.
     */
    public static class Iterator implements java.util.Iterator<Integer> {
        private Node node;

        public Iterator(Node head) {
            this.node = this.containsOddNumber(head) ? head : this.getNextOddNode(head);
        }

        @Override
        public boolean hasNext() {
            return this.node != null;
        }

        @Override
        public Integer next() {
            Integer data = this.node.getData();
            this.node = this.getNextOddNode(this.node);
            return data;
        }

        private Node getNextOddNode(Node node) {
            Node next = node;
            while ((next = next.getNext()) != null) if (this.containsOddNumber(next)) return next;
            return next;
        }

        private boolean containsOddNumber(Node node) {
            return Math.abs(node.getData()) % 2 == 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    //----------------------------------------------------------------//
    public static void main(String[] args) {
        int[] intArray = {-42, 6, -43, 3, 41, -48, -7, -16, 42, -30, -21, 27, 17, -8, -3, -21, 15, 9, -30, 19, -39, -34, -41, -15, 28, 24, 22, -20, -23, 17, -19, 45, -14, -26, -30, 34, -9, -36, 35, 13};
        MySList mysl = new MySList();
        for (int element : intArray) mysl.push_back(element);
        for (Integer integer : mysl) System.out.println(integer);
    }

}
