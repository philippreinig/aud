package lectures.lists.SinglyLinkedList;

public class SinglyLinkedList<T> {

    Node head;

    public SinglyLinkedList() {

    }

    public int size() {
        int counter = 0;
        Node nextNode = head;
        while (nextNode != null) {
            counter++;
            nextNode = nextNode.getNextNode();
        }
        return counter;
    }

    public T getDataAt(int i) throws SinglyLinkedListOutofBoundsException {
        if (i < 0 || i >= this.size() || this.isEmpty()) {
            throw new SinglyLinkedListOutofBoundsException(i);
        }
        Node node = head;
        for (int j = 0; j != i; j++) {
            node = node.getNextNode();
        }
        return node.getData();

    }

    public boolean isEmpty() {
        return head == null;
    }

    public void push_back(T obj) {
        if (head == null) {
            head = new Node(obj, null);
        } else {
            Node node = head;
            while (node.getNextNode() != null) {
                node = node.getNextNode();
            }
            Node lastNode = node;
            lastNode.setNextNode(new Node(obj, null));
        }
    }

    public void insert_after(Node node, T obj) {
        if (node == null) {
            head = new Node(obj, head);
        } else {
            node.setNextNode(new Node(obj, node.getNextNode()));
        }
    }

    public void insertAt(int i, T obj) throws SinglyLinkedListOutofBoundsException {
        if (i < 0 || i > this.size()) {
            throw new SinglyLinkedListOutofBoundsException(i);
        }
        if (i == 0) {
            head = new Node(obj, head);
        } else {
            Node node = head;
            for (int j = 0; j != i; j++) {
                node = node.getNextNode();
            }
            node.setNextNode(new Node(obj, node.getNextNode()));
        }
    }

    public void pop_back() throws EmptySinglyLinkedListException {
        if (head == null) {
            throw new EmptySinglyLinkedListException();
        } else {
            Node node = head;
            Node previousNode = null;
            while (node.getNextNode() != null) {
                previousNode = node;
                node = node.getNextNode();
            }
            if (previousNode == null) head = null;
            else node.setNextNode(null);
        }
    }

    public void erase(int i) throws SinglyLinkedListOutofBoundsException {
        if (i < 0 || i >= this.size() || this.isEmpty()) {
            throw new SinglyLinkedListOutofBoundsException(i);
        }
        if (i == 0) {
            head = head.getNextNode();
        }
        Node node = head;
        for (int j = 0; j < i - 1; j++) {
            node = node.getNextNode();
        }
        node.setNextNode(node.getNextNode().getNextNode());
    }

    @Override
    public String toString() {
        if (this.isEmpty()) return "[]";
        else {
            String temp = "[";
            Node node = head;
            while (node.getNextNode() != null) {
                temp += node.getData().toString();
                node = node.getNextNode();
            }
            temp += node.getData().toString() + "]";
            return temp;
        }

    }

    class Node {
        private final T data;
        private Node nextNode;

        public Node(T data, Node nextNode) {
            this.data = data;
            this.nextNode = nextNode;
        }

        public T getData() {
            return this.data;
        }

        public Node getNextNode() {
            return this.nextNode;
        }

        public void setNextNode(Node nextNode) {
            this.nextNode = nextNode;
        }
    }
}
