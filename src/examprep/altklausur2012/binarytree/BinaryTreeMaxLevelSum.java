package examprep.altklausur2012.binarytree;

public class BinaryTreeMaxLevelSum {

    public static void main(final String[] args) {
        final BinaryTree bt1 = new BinaryTree(32, null, null, null);
        final BinaryTree bt2 = new BinaryTree(2, null, null, bt1);
        final BinaryTree bt3 = new BinaryTree(3, null, null, bt1);
        final BinaryTree bt4 = new BinaryTree(4, null, null, bt2);
        final BinaryTree bt5 = new BinaryTree(5, null, null, bt2);
        final BinaryTree bt6 = new BinaryTree(6, null, null, bt3);
        final BinaryTree bt7 = new BinaryTree(7, null, null, bt3);
        final BinaryTree bt8 = new BinaryTree(8, null, null, bt4);
        final BinaryTree bt9 = new BinaryTree(9, null, null, bt4);
        final BinaryTree bt10 = new BinaryTree(10, null, null, bt5);

        bt1.setLeft(bt2);
        bt1.setRight(bt3);
        bt2.setLeft(bt4);
        bt2.setRight(bt5);
        bt3.setLeft(bt6);
        bt3.setRight(bt7);
        bt4.setLeft(bt8);
        bt4.setRight(bt9);
        bt5.setLeft(bt10);

        System.out.println(bt1.maxLevelSum());

    }
}
