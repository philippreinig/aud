package exercises.week04;

import aud.BinaryTree;
import aud.Queue;
import aud.util.DotViewer;

public class BuildBinTree<T> {
    public static void main(final String[] args) {
        final BinaryTree<Integer> rightChild21 = new BinaryTree<>(-11, null, null);
        final BinaryTree<Integer> leftChild21 = new BinaryTree<>(6, null, null);
        final BinaryTree<Integer> leftChild1 = new BinaryTree<>(4, leftChild21, rightChild21);
        final BinaryTree<Integer> leftChild22 = new BinaryTree<>(5);
        final BinaryTree<Integer> rightChild22 = new BinaryTree<>(7);
        final BinaryTree<Integer> rightChild1 = new BinaryTree<>(1, leftChild22, rightChild22);
        final BinaryTree<Integer> tree = new BinaryTree<>(-8, leftChild1, rightChild1);

        final BuildBinTree<Integer> bbb = new BuildBinTree<>();
        bbb.printAll(tree);


        DotViewer.displayWindow(tree, "BinaryTree").setExitOnClose();
    }

    public void printPreorder(final BinaryTree<T> node) {
        if (node != null) {
            System.out.print(node.getData().toString() + " ");
            this.printPreorder(node.getLeft());
            this.printPreorder(node.getRight());
        }
    }

    public void printInorder(final BinaryTree<T> node) {
        if (node != null) {
            this.printPreorder(node.getLeft());
            System.out.print(node.getData().toString() + " ");
            this.printPreorder(node.getRight());
        }
    }

    public void printPostorder(final BinaryTree<T> node) {
        if (node != null) {
            this.printPreorder(node.getLeft());
            this.printPreorder(node.getRight());
            System.out.print(node.getData().toString() + " ");
        }
    }

    public void printLevelorder(final BinaryTree<T> tree) {
        final Queue<BinaryTree<T>> queue = new Queue<>();
        queue.enqueue(tree.getRoot()); // initialize
        while (!queue.isEmpty()) {
            final BinaryTree<T> node = queue.dequeue();
            if (node != null) {
                queue.enqueue(node.getLeft()); // !!
                queue.enqueue(node.getRight());
                System.out.print(node.getData().toString() + " "); // == return next entry
            }
        }
    }

    public void printAll(final BinaryTree<T> tree) {
        System.out.println("Printing tree in preorder: ");
        this.printPreorder(tree);
        System.out.println();
        System.out.println("Printing tree in inorder: ");
        this.printInorder(tree);
        System.out.println();
        System.out.println("Printing tree in postorder: ");
        this.printPostorder(tree);
        System.out.println();
        System.out.println("Printing tree in levelorder: ");
        this.printLevelorder(tree);
        System.out.println();
    }
}
