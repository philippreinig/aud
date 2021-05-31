package exercises.week08.MyBTree;

import aud.BTree;
import aud.KTreeNode;
import aud.util.DotViewer;
import aud.util.SingleStepper;

public class MyBTree<Key extends Comparable<Key>> extends BTree<Key> {
    public MyBTree(int m) {
        super(m);
    }

    public int getHeight() {
        KTreeNode<Key> node = this.root();
        int height_counter = 0;
        while (node != null) {
            height_counter++;
            node = node.getChild(0);
        }
        return height_counter;
    }

    private static void showExampleTree(){
        int m = 2;
        MyBTree<Integer> mbt = new MyBTree<>(m);
        SingleStepper ss = new SingleStepper("Single Stepper");
        DotViewer dv = DotViewer.displayWindow(mbt, "MyBTree testing");
        ss.halt();
        mbt.insert(6);
        dv.display(mbt);

        ss.halt();
        mbt.insert(18);
        dv.display(mbt);

        ss.halt();
        mbt.insert(22);
        dv.display(mbt);

        ss.halt();
        mbt.insert(3);
        dv.display(mbt);

        ss.halt();
        mbt.insert(11);
        dv.display(mbt);

        ss.halt();
        mbt.insert(16);
        dv.display(mbt);

        ss.halt();
        mbt.insert(7);
        dv.display(mbt);

        ss.halt();
        mbt.insert(10);
        dv.display(mbt);

        ss.halt();
        mbt.insert(1);
        dv.display(mbt);

        ss.halt();
        mbt.insert(8);
        dv.display(mbt);

        ss.halt();
        mbt.insert(12);
        dv.display(mbt);

        ss.halt();
        mbt.insert(6);
        dv.display(mbt);

        System.out.println(mbt.getHeight());
    }

    public static void main(String[] args) {
        int m = 1000;
        MyBTree<Integer> mbt = new MyBTree<>(m);
       for (int i = 0; i < 1000000; i++){
           mbt.insert(i);
       }
        System.out.println(mbt.getHeight());
        //DotViewer.displayWindow(mbt, "MyBinaryTreeDisplay");


    }
}
