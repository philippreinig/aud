package exercises.week05;

import aud.BinaryTree;
import aud.Queue;
import aud.util.DotViewer;

//------------------------------------------------------------------//
public class MyBinTree<T> extends BinaryTree<T> {
    //----------------------------------------------------------------//
    public MyBinTree(final T data) {
        super(data);
    }

    //----------------------------------------------------------------//
    public MyBinTree(final T data, final MyBinTree<T> left, final MyBinTree<T> right) {
        super(data, left, right);
    }

    //---------------------------------------------------------------//
    public static void main(final String[] args) {
        final MyBinTree<Integer> rightChild21 = new MyBinTree<>(-11, null, null);
        final MyBinTree<Integer> leftChild21 = new MyBinTree<>(6, null, null);
        final MyBinTree<Integer> leftChild1 = new MyBinTree<>(4, leftChild21, rightChild21);
        final MyBinTree<Integer> leftChild22 = new MyBinTree<>(5);
        final MyBinTree<Integer> rightChild22 = new MyBinTree<>(7);
        final MyBinTree<Integer> rightChild1 = new MyBinTree<>(1, leftChild22, rightChild22);
        final MyBinTree<Integer> tree = new MyBinTree<>(-8, leftChild1, rightChild1);
        tree.traverse(tree);

        System.out.println("Height of tree: " + tree.getHeight());
        System.out.println("Height of subtree 4: " + leftChild1.getHeight());
        System.out.println("Height of subtree 6: " + leftChild21.getHeight());

        System.out.println("Width on level 1 is: " + tree.getWidthOnLevel(tree, 1));
        System.out.println("Width on level 1 is: " + tree.getWidthOnLevel(tree, 2));
        System.out.println("Width on level 1 is: " + tree.getWidthOnLevel(tree, 3));

        System.out.println("Max width of tree is: " + tree.maxWidth());

        DotViewer.displayWindow(tree.toDot(), "testing trees");
    }

    private void traverse(final MyBinTree<T> root) {
        final Queue<MyBinTree<T>> queue = new Queue<>();
        queue.enqueue(root);
        MyBinTree<T> node;
        while (!queue.isEmpty()) {
            node = queue.dequeue();
            if (node != null) {
                queue.enqueue((MyBinTree<T>) node.getLeft());
                queue.enqueue((MyBinTree<T>) node.getRight());
                System.out.println(node.getData().toString());
            }
        }
    }

    private int getWidthOnLevel(final MyBinTree<T> tree, final int level) {
        assert (level >= 0) : "tree doesnt have negative levels";
        if (tree == null) return 0;
        else if (level == 1) return 1;
        else
            return this.getWidthOnLevel((MyBinTree<T>) tree.getLeft(), level - 1) + this.getWidthOnLevel((MyBinTree<T>) tree.getRight(), level - 1);
    }

    private int getHeight() {
        //System.out.println("Getting height of tree with root: " + this.getData().toString());
        final MyBinTree<T> node = this;

        final int heightLeft = node.getLeft() != null ? ((MyBinTree<T>) node.getLeft()).getHeight() : 0;
        final int heightRight = node.getRight() != null ? ((MyBinTree<T>) node.getRight()).getHeight() : 0;

        final int maxHeight = Math.max(heightLeft, heightRight);
        return maxHeight + 1;
    }

    //----------------------------------------------------------------//
    public int maxWidth() {
        int maxWidth = 0;
        for (int i = 1; i <= this.getHeight(); i++) {
            final int widthLevelI = this.getWidthOnLevel(this, i);
            if (widthLevelI > maxWidth) maxWidth = widthLevelI;
        }
        return maxWidth;
        // TODO:
        // - implement to return the maximum width of the binary tree
        // - you can use a queue for your solution
    }
}