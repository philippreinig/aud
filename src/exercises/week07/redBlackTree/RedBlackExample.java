package exercises.week07.redBlackTree;

import aud.RedBlackTree;
import aud.util.DotViewer;
import aud.util.SingleStepper;

//-----------------------------------------------------------------//
public class RedBlackExample {
    //---------------------------------------------------------------//
    public static void main(String[] args) {
        RedBlackTree<Integer, Integer> redBlackTree = new RedBlackTree<>();
        SingleStepper ss = new SingleStepper("Single Stepper");
        DotViewer dv = DotViewer.displayWindow(redBlackTree, "AVL tree testing");

        ss.halt();
        redBlackTree.insert(6, 6);
        dv.display(redBlackTree);

        ss.halt();
        redBlackTree.insert(7, 7);
        dv.display(redBlackTree);

        ss.halt();
        redBlackTree.insert(3, 3);
        dv.display(redBlackTree);

        ss.halt();
        redBlackTree.insert(4, 4);
        dv.display(redBlackTree);

        ss.halt();
        redBlackTree.insert(2, 2);
        dv.display(redBlackTree);

        ss.halt();
        redBlackTree.insert(1, 1);
        dv.display(redBlackTree);

    }
}
