package adhoc;

import aud.BinaryTree;
import aud.util.DotViewer;

public class BinaryTreeAdHoc<T> extends BinaryTree<T> {
    public BinaryTreeAdHoc(T data) {
        super(data);
    }

    public BinaryTreeAdHoc<T> getParent() {
        return (BinaryTreeAdHoc<T>) super.getParent();
    }

    public BinaryTreeAdHoc<T> getLeft() {
        return (BinaryTreeAdHoc<T>) super.getLeft();
    }

    public BinaryTreeAdHoc<T> getRight() {
        return (BinaryTreeAdHoc<T>) super.getRight();
    }

    public T getData() {
        return super.getData();
    }

    public int height() {
        int heightLeft = this.getLeft() != null ? this.getLeft().height() : 0;
        int heightRight = this.getRight() != null ? this.getRight().height() : 0;
        int maxHeight = Math.max(heightLeft, heightRight);
        return maxHeight + 1;
    }

    public boolean isBalanced() {
        if (this.getLeft() == null && this.getRight() == null) return true;
        if (this.getLeft() == null && this.getRight() != null) return !(this.getRight().height() > 1);
        if (this.getLeft() != null && this.getRight() == null) return !(this.getLeft().height() > 1);

        else
            return (this.getLeft().isBalanced()) && (this.getRight().isBalanced()) && (Math.abs(this.getLeft().height() - this.getRight().height()) <= 1);
    }

    public static void main(String[] args) {
        BinaryTreeAdHoc<Integer> root = new BinaryTreeAdHoc<>(0);
        BinaryTreeAdHoc<Integer> leftChild1 = new BinaryTreeAdHoc<>(1);
        BinaryTreeAdHoc<Integer> rightChild1 = new BinaryTreeAdHoc<>(2);
        BinaryTreeAdHoc<Integer> leftChild2 = new BinaryTreeAdHoc<>(3);
        BinaryTreeAdHoc<Integer> leftChild3 = new BinaryTreeAdHoc<>(4);
        BinaryTreeAdHoc<Integer> rightChild1leftChild = new BinaryTreeAdHoc<>(5);
        BinaryTreeAdHoc<Integer> rightChild1leftChild2 = new BinaryTreeAdHoc<>(6);

        root.setLeft(leftChild1);
        root.setRight(rightChild1);
        leftChild1.setLeft(leftChild2);
        rightChild1.setLeft(rightChild1leftChild);
        rightChild1leftChild.setLeft(rightChild1leftChild2);
        //leftChild2.setLeft(leftChild3);

        DotViewer.displayWindow(root.toDot(), "tree");
        System.out.println(root.isBalanced());
    }
}
