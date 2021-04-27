package lectures.lists.SinglyLinkedList;

public class EmptySinglyLinkedListException extends Exception {
    public EmptySinglyLinkedListException() {
        super("Can't delete last object from an empty SinglyLinkedList!");
    }
}
