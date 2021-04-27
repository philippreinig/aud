package lectures.lists.SinglyLinkedList;

public class SinglyLinkedListOutofBoundsException extends Exception {
    public SinglyLinkedListOutofBoundsException(int i) {
        super(i + " is not a valid index");
    }
}
