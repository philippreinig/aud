package examprep.altklausur2012.binarytree;

import aud.util.DotViewer;

public class BinarySearchTreeFind {

    public static void main(final String[] args) {
        final BinaryTree bt1 = new BinaryTree(10, null, null, null);
        final BinaryTree bt2 = new BinaryTree(5, null, null, bt1);
        final BinaryTree bt3 = new BinaryTree(15, null, null, bt1);
        final BinaryTree bt4 = new BinaryTree(3, null, null, bt2);
        final BinaryTree bt5 = new BinaryTree(7, null, null, bt2);
        final BinaryTree bt6 = new BinaryTree(13, null, null, bt3);
        final BinaryTree bt7 = new BinaryTree(17, null, null, bt3);
        final BinaryTree bt8 = new BinaryTree(2, null, null, bt4);
        final BinaryTree bt9 = new BinaryTree(14, null, null, bt6);
        final BinaryTree bt10 = new BinaryTree(1, null, null, bt8);

        bt1.setLeft(bt2);
        bt1.setRight(bt3);
        bt2.setLeft(bt4);
        bt2.setRight(bt5);
        bt3.setLeft(bt6);
        bt3.setRight(bt7);
        bt4.setLeft(bt8);
        bt8.setLeft(bt10);
        bt6.setRight(bt9);


        for (int i = 0; i < 20; ++i) {
            final BinaryTree findResult = bt1.find(i);
            final BinaryTree find2Result = bt1.find2(i);
            final BinaryTree find3Result = bt1.find3(i);
            System.out.println(findResult + ", " + find2Result + ", " + find3Result);
        }

        final DotViewer dv = DotViewer.displayWindow(bt1.toDot(), "Binary Tree");

    }
}
