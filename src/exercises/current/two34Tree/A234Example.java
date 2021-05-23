package exercises.current.two34Tree;

import aud.A234Tree;
import aud.AVLTree;
import aud.util.DotViewer;
import aud.util.SingleStepper;

public class A234Example {
    public static void main(String[] args) {
        A234Tree<Integer> a234Tree = new A234Tree<>(false);
        SingleStepper ss = new SingleStepper("Single Stepper");
        DotViewer dv = DotViewer.displayWindow(a234Tree, "AVL tree testing");

        ss.halt();
        a234Tree.insert(3);
        dv.display(a234Tree);

        ss.halt();
        a234Tree.insert(5);
        dv.display(a234Tree);

        ss.halt();
        a234Tree.insert(7);
        dv.display(a234Tree);

        ss.halt();
        a234Tree.insert(9);
        dv.display(a234Tree);

        ss.halt();
        a234Tree.insert(11);
        dv.display(a234Tree);

        ss.halt();
        a234Tree.insert(13);
        dv.display(a234Tree);

        ss.halt();
        a234Tree.insert(15);
        dv.display(a234Tree);

        ss.halt();
        a234Tree.insert(17);
        dv.display(a234Tree);

        ss.halt();
        a234Tree.insert(19);
        dv.display(a234Tree);

        ss.halt();
        a234Tree.insert(21);
        dv.display(a234Tree);
    }
}
