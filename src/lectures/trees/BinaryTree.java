package lectures.trees;

import aud.Stack;

public class BinaryTree<T> {
    private BinaryTree<T> parent;
    private BinaryTree<T> leftChild;
    private BinaryTree<T> rightChild;
    private T data;

    public BinaryTree(final BinaryTree<T> parent, final BinaryTree<T> leftChild, final BinaryTree<T> rightChild, final T data) {
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.data = data;
        if (this.getLeftChild() != null) this.getLeftChild().setParent(this);
        if (this.getRightChild() != null) this.getRightChild().setParent(this);
    }

    public T getData() {
        return this.data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public BinaryTree<T> getRightChild() {
        return this.rightChild;
    }

    public void setRightChild(final BinaryTree<T> rightChild) {
        final BinaryTree<T> oldRightChild = this.getRightChild();
        this.rightChild = rightChild;
        if (this.rightChild != null) this.rightChild.setParent(this);
        if (oldRightChild != null) oldRightChild.setParent(null);
    }

    public BinaryTree<T> getLeftChild() {
        return this.leftChild;
    }

    public void setLeftChild(final BinaryTree<T> leftChild) {
        final BinaryTree<T> oldLeftChild = this.getLeftChild();
        this.leftChild = leftChild;
        if (this.leftChild != null) this.leftChild.setParent(this);
        if (oldLeftChild != null) oldLeftChild.setParent(null);
    }

    public BinaryTree<T> getParent() {
        return this.parent;
    }

    public void setParent(final BinaryTree<T> parent) {
        this.parent = parent;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isLeaf() {
        return this.getLeftChild() == null && this.getRightChild() == null;
    }

    public BinaryTree<T> getRoot() {
        BinaryTree<T> node = this;
        while (node.getParent() != null) node = node.getParent();
        return node;
    }

    public int niveau() {
        BinaryTree<T> parent = this.getParent();
        int i = 0;
        while (!parent.isRoot()) {
            i++;
            parent = parent.getParent();
        }
        return i;
    }

    public void traversePreorder(final BinaryTree<T> node) {
        if (node != null) {
            System.out.print(node.getData().toString() + " ");
            this.traversePreorder(node.getLeftChild());
            this.traversePreorder(node.getRightChild());
        }
    }

    public void traversePreorderIterative(BinaryTree<T> node) {
        final Stack<BinaryTree<T>> stack = new Stack<>();
        while (node != null) {
            while (node != null) {
                System.out.print(node.getData() + " ");
                if (node.getRightChild() != null) stack.push(node.getRightChild());
                node = node.getLeftChild();
            }
            node = stack.isEmpty() ? null : stack.pop();
        }
    }

    public void traversePreorderIterativeEasy(BinaryTree<T> node) {
        final Stack<BinaryTree<T>> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            node = stack.pop();
            if (node.getRightChild() != null) stack.push(node.getRightChild());
            if (node.getLeftChild() != null) stack.push(node.getLeftChild());
            System.out.print(node.getData() + " ");
        }
    }

    public void traverseInorder(final BinaryTree<T> node) {
        if (node != null) {
            this.traversePreorder(node.getLeftChild());
            System.out.print(node);
            this.traversePreorder(node.getRightChild());
        }
    }

    public void traversePostOrder(final BinaryTree<T> node) {
        if (node != null) {
            this.traversePreorder(node.getLeftChild());
            System.out.println(node);
            this.traversePreorder(node.getRightChild());
        }
    }
//    public int height() {
//        int height = getHeight(0);
//        return this.niveau() + height;
//    }
//
//    private int getHeight(int currentMaxHeight) {
//        if (this.leftChild == null && this.getRightChild() == null) return currentMaxHeight;
//        int maxHeightLeftChild = this.getLeftChild() != null ? this.getLeftChild().getHeight(currentMaxHeight++) : currentMaxHeight;
//        int maxHeightRichtChild = this.getRightChild() != null ? this.getRightChild().getHeight(currentMaxHeight++) : currentMaxHeight;
//        return Math.max(maxHeightLeftChild, maxHeightRichtChild);
//    }

    @Override
    public String toString() {
        final String parent = this.getParent() == null ? "null" : this.getParent().getData().toString();
        final String leftChild = this.getLeftChild() == null ? "null" : this.getLeftChild().getData().toString();
        final String rightChild = this.getRightChild() == null ? "null" : this.getRightChild().getData().toString();
        return "data: " + this.getData() + ", parent: " + parent + ", leftChild: " + leftChild + ", rightChild " + rightChild;
    }
}
