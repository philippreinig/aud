package lectures.trees;

import aud.Stack;

public class BinaryTree<T> {
    private BinaryTree<T> parent;
    private BinaryTree<T> leftChild;
    private BinaryTree<T> rightChild;
    private T data;

    public BinaryTree(BinaryTree<T> parent, BinaryTree<T> leftChild, BinaryTree<T> rightChild, T data) {
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

    public void setData(T data) {
        this.data = data;
    }

    public BinaryTree<T> getRightChild() {
        return this.rightChild;
    }

    public void setRightChild(BinaryTree<T> rightChild) {
        BinaryTree<T> oldRightChild = this.getRightChild();
        this.rightChild = rightChild;
        if (this.rightChild != null) this.rightChild.setParent(this);
        if (oldRightChild != null) oldRightChild.setParent(null);
    }

    public BinaryTree<T> getLeftChild() {
        return this.leftChild;
    }

    public void setLeftChild(BinaryTree<T> leftChild) {
        BinaryTree<T> oldLeftChild = this.getLeftChild();
        this.leftChild = leftChild;
        if (this.leftChild != null) this.leftChild.setParent(this);
        if (oldLeftChild != null) oldLeftChild.setParent(null);
    }

    public BinaryTree<T> getParent() {
        return this.parent;
    }

    public void setParent(BinaryTree<T> parent) {
        this.parent = parent;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return this.getLeftChild() == null && this.getRightChild() == null;
    }

    public BinaryTree<T> getRoot() {
        BinaryTree<T> node = this;
        while (node.getParent() != null) {
            node = node.getParent();
        }
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

    public void traversePreorder(BinaryTree<T> node) {
        if (node != null) {
            System.out.print(node.getData().toString() + " ");
            traversePreorder(node.getLeftChild());
            traversePreorder(node.getRightChild());
        }
    }

    public void traversePreorderIterative(BinaryTree<T> node) {
        Stack<BinaryTree<T>> stack = new Stack<>();
        while (node != null) {
            while (node != null) {
                System.out.print(node.getData() + " ");
                if (node.getRightChild() != null) {
                    stack.push(node.getRightChild());
                }
                node = node.getLeftChild();
            }
            node = stack.is_empty() ? null : stack.pop();
        }
    }

    public void traversePreorderIterativeEasy(BinaryTree<T> node) {
        Stack<BinaryTree<T>> stack = new Stack<>();
        stack.push(node);
        while (!stack.is_empty()) {
            node = stack.pop();
            if (node.getRightChild() != null) stack.push(node.getRightChild());
            if (node.getLeftChild() != null) stack.push(node.getLeftChild());
            System.out.print(node.getData() + " ");
        }
    }

    public void traverseInorder(BinaryTree<T> node) {
        if (node != null) {
            traversePreorder(node.getLeftChild());
            System.out.print(node);
            traversePreorder(node.getRightChild());
        }
    }

    public void traversePostOrder(BinaryTree<T> node) {
        if (node != null) {
            traversePreorder(node.getLeftChild());
            System.out.println(node);
            traversePreorder(node.getRightChild());
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
        String parent = this.getParent() == null ? "null" : this.getParent().getData().toString();
        String leftChild = this.getLeftChild() == null ? "null" : this.getLeftChild().getData().toString();
        String rightChild = this.getRightChild() == null ? "null" : this.getRightChild().getData().toString();
        return "data: " + this.getData() + ", parent: " + parent + ", leftChild: " + leftChild + ", rightChild " + rightChild;
    }
}
