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


        /*
        AVLTree<Integer, String> test = new AVLTree<Integer, String>();
	  	SingleStepper stepper = new SingleStepper("hi");
	  	DotViewer v = DotViewer.displayWindow(test, "Dot Viewer");

		 stepper.halt();
		 test.insert(14, "a");
		 v.display(test);

		 stepper.halt();
		 test.insert(15, "b");
		 v.display(test);

		 stepper.halt();
		 test.insert(17, "c");
		 v.display(test);

		 stepper.halt();
		 test.insert(7, "d");
		 v.display(test);

		 stepper.halt();
		 test.insert(5, "e");
		 v.display(test);

		 stepper.halt();
		 test.insert(10, "f");
		 v.display(test);

		 stepper.halt();
		 test.insert(16, "g");
		 v.display(test);
         */
    }
}
