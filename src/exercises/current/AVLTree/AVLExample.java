package exercises.current.AVLTree;

import aud.AVLTree;
import aud.util.DotViewer;
import aud.util.SingleStepper;

import javax.swing.*;

//14, 15, 17, 7, 5, 10, 16
public class AVLExample {
    public static void main(String[] args) {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        SingleStepper ss = new SingleStepper("Single Stepper");
        DotViewer dv = DotViewer.displayWindow(avlTree, "AVL tree testing");

        ss.halt();
        avlTree.insert(14, 14);
        dv.display(avlTree);

        ss.halt();
        avlTree.insert(15, 15);
        dv.display(avlTree);

        ss.halt();
        avlTree.insert(17, 17);
        dv.display(avlTree);

        ss.halt();
        avlTree.insert(7, 7);
        dv.display(avlTree);

        ss.halt();
        avlTree.insert(5, 5);
        dv.display(avlTree);

        ss.halt();
        avlTree.insert(10, 10);
        dv.display(avlTree);

        ss.halt();
        avlTree.insert(16, 16);
        dv.display(avlTree);
    }
}
