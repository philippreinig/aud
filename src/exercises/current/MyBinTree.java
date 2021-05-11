package exercises.current;

import aud.util.*;        //to use DotViewer, SingleStepper
import aud.BinaryTree;    //to use BinaryTree<T>
import aud.Queue;         //to use Queue<T>

//------------------------------------------------------------------//
public class MyBinTree<T> extends BinaryTree<T> {
    //----------------------------------------------------------------//
    public MyBinTree(T data) {
        super(data);
    }

    //----------------------------------------------------------------//
    public MyBinTree(T data,MyBinTree<T> left,MyBinTree<T> right) {
        super(data, left, right);
    }

    private void traverse(MyBinTree<T> root){
        Queue<MyBinTree<T>> queue = new Queue<>();
        queue.enqueue(root);
        MyBinTree<T> node;
        while(!queue.is_empty()){
            node = queue.dequeue();
            if(node != null) {
                queue.enqueue((MyBinTree<T>) node.getLeft());
                queue.enqueue((MyBinTree<T>) node.getRight());
                System.out.println(node.getData().toString());
            }
            }
    }

    private int getWidthOnLevel(MyBinTree<T> tree, int level){
        assert (level >= 0) : "tree doesnt have negative levels";
        if (tree == null) return 0;
        else if (level == 1) return 1;
        else return getWidthOnLevel((MyBinTree<T>) tree.getLeft(), level-1) + getWidthOnLevel((MyBinTree<T>) tree.getRight(), level-1);
    }

    private int getHeight(){
        //System.out.println("Getting height of tree with root: " + this.getData().toString());
        MyBinTree<T> node = this;

        int heightLeft = node.getLeft() != null ? ((MyBinTree<T>) node.getLeft()).getHeight() : 0;
        int heightRight = node.getRight() != null ? ((MyBinTree<T>) node.getRight()).getHeight() : 0;

        int maxHeight = Math.max(heightLeft, heightRight);
        return maxHeight + 1;
    }

    //----------------------------------------------------------------//
    public int maxWidth() {
        int maxWidth = 0;
        for (int i = 1; i <= this.getHeight(); i++){
            int widthLevelI = this.getWidthOnLevel(this, i);
            if (widthLevelI > maxWidth) maxWidth = widthLevelI;
        }
        return maxWidth;
        // TODO:
        // - implement to return the maximum width of the binary tree
        // - you can use a queue for your solution
    }

    //---------------------------------------------------------------//
    public static void main(String[] args) {
        MyBinTree<Integer> rightChild21 = new MyBinTree<>(-11, null, null);
        MyBinTree<Integer> leftChild21 = new MyBinTree<>(6, null, null);
        MyBinTree<Integer> leftChild1 = new MyBinTree<>(4, leftChild21, rightChild21);
        MyBinTree<Integer> leftChild22 = new MyBinTree<>(5);
        MyBinTree<Integer> rightChild22 = new MyBinTree<>(7);
        MyBinTree<Integer> rightChild1 = new MyBinTree<>(1, leftChild22, rightChild22);
        MyBinTree<Integer> tree = new MyBinTree<>(-8, leftChild1, rightChild1);
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
}