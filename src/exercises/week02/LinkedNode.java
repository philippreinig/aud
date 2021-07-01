package exercises.week02;

public class LinkedNode<T> {
    T data = null;
    LinkedNode<T> nextNode = null;

    public LinkedNode(T data, LinkedNode<T> nextNode) {
        this.data = data;
        this.nextNode = nextNode;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LinkedNode<T> getNextNode() {
        return this.nextNode;
    }

    public void setNextNode(LinkedNode<T> nextNode) {
        this.nextNode = nextNode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        LinkedNode<T> nextNode = this;
        while (nextNode.getNextNode() != null) {
            sb.append(nextNode.getData().toString());
            sb.append(", ");
            nextNode = nextNode.getNextNode();
        }
        sb.append(nextNode.getData().toString());
        sb.append("]");
        return sb.toString();
    }

    // You must provide a main() method!
    public static void main(String[] args) {
        LinkedNode<String> monday = new LinkedNode<>("monday", null);
        LinkedNode<String> tuesday = new LinkedNode<>("tuesday", null);
        monday.setNextNode(tuesday);
        LinkedNode<String> thursday = new LinkedNode<>("thursday", null);
        tuesday.setNextNode(thursday);
        LinkedNode<String> friday = new LinkedNode<>("friday", null);
        thursday.setNextNode(friday);
        System.out.println(monday);
        System.out.println(tuesday);
        System.out.println(thursday);
        System.out.println(friday);

        System.out.println("-----------------------------------------");
        LinkedNode<String> wednesday = new LinkedNode<>("wednesday", thursday);
        tuesday.setNextNode(wednesday);

        System.out.println(monday);
        System.out.println(tuesday);
        System.out.println(wednesday);
        System.out.println(thursday);
        System.out.println(friday);

//        LinkedNode<Character> ln1 = new LinkedNode<>('a', null);
//        ln1.setNextNode(new LinkedNode<Character>('b', null));
//        Character ln1Data = ln1.getData();
//        Character ln2Data = ln1.getNextNode().getData();
//        System.out.println(ln1Data);
//        System.out.println(ln2Data);
//        System.out.println(ln1.toString());
//        System.out.println(ln1.getNextNode().toString());
    }
}