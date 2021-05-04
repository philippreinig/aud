package exercises.current;

import aud.BinaryTree;
import aud.Queue;
import aud.util.DotViewer;

public class BuildBinTree<T> {
    public void printPreorder(BinaryTree<T> node) {
        if (node != null) {
            System.out.print(node.getData().toString() + " ");
            printPreorder(node.getLeft());
            printPreorder(node.getRight());
        }
    }

    public void printInorder(BinaryTree<T> node) {
        if (node != null) {
            printPreorder(node.getLeft());
            System.out.print(node.getData().toString() + " ");
            printPreorder(node.getRight());
        }
    }

    public void printPostorder(BinaryTree<T> node) {
        if (node != null) {
            printPreorder(node.getLeft());
            printPreorder(node.getRight());
            System.out.print(node.getData().toString() + " ");
        }
    }

    public void printLevelorder(BinaryTree<T> tree){
        Queue<BinaryTree<T>> queue = new Queue<>();
        queue.enqueue (tree.getRoot()) ; // initialize
        while (!queue.is_empty()){
            BinaryTree<T> node = queue.dequeue () ;
            if ( node != null ) {
                queue . enqueue ( node . getLeft () ) ; // !!
                queue . enqueue ( node . getRight () ) ;
                System.out.print(node.getData().toString() + " "); // == return next entry
            }
        }
    }

    public void printAll(BinaryTree<T> tree){
        System.out.println("Printing tree in preorder: ");
        printPreorder(tree);
        System.out.println();
        System.out.println("Printing tree in inorder: ");
        printInorder(tree);
        System.out.println();
        System.out.println("Printing tree in postorder: ");
        printPostorder(tree);
        System.out.println();
        System.out.println("Printing tree in levelorder: ");
        printLevelorder(tree);
        System.out.println();
    }

    public static void main(String[] args) {
        BinaryTree<Integer> rightChild21 = new BinaryTree<>(-11, null, null);
        BinaryTree<Integer> leftChild21 = new BinaryTree<>(6, null, null);
        BinaryTree<Integer> leftChild1 = new BinaryTree<>(4, leftChild21, rightChild21);
        BinaryTree<Integer> leftChild22 = new BinaryTree<>(5);
        BinaryTree<Integer> rightChild22 = new BinaryTree<>(7);
        BinaryTree<Integer> rightChild1 = new BinaryTree<>(1, leftChild22, rightChild22);
        BinaryTree<Integer> tree = new BinaryTree<>(-8, leftChild1, rightChild1);

        BuildBinTree<Integer> bbb = new BuildBinTree<>();
        bbb.printAll(tree);


        DotViewer.displayWindow(tree, "BinaryTree").setExitOnClose();
    }
}
