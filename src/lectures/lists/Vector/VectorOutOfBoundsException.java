package lectures.lists.Vector;

public class VectorOutOfBoundsException extends Exception {
    VectorOutOfBoundsException(int i) {
        super(i + " is not a valid index");
    }

}
