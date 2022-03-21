package examprep.altklausur2012.binarytree;

import aud.Queue;
import aud.Stack;
import aud.util.Graphvizable;

public class BinaryTree implements Graphvizable {
    private final int data;
    private BinaryTree left;
    private BinaryTree right;
    private BinaryTree parent;

    public BinaryTree(final int data) {
        this(data, null, null, null);
    }

    public BinaryTree(final int data, final BinaryTree left, final BinaryTree right, final BinaryTree parent) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public int getData() {
        return this.data;
    }

    public BinaryTree getLeft() {
        return this.left;
    }

    public void setLeft(final BinaryTree left) {
        this.left = left;
    }

    public BinaryTree getRight() {
        return this.right;
    }

    public void setRight(final BinaryTree right) {
        this.right = right;
    }

    public BinaryTree getParent() {
        return this.parent;
    }

    public void setParent(final BinaryTree parent) {
        this.parent = parent;
    }

    public BinaryTree find(final int x) {
        BinaryTree next = this;
        while (next != null) {
            if (next.getData() == x) return next;
            else if (x < next.getData()) next = next.getLeft();
            else next = next.getRight();
        }

        return null;
    }

    public BinaryTree find2(final int x) {
        if (this.getData() == x) return this;
        else if (this.getLeft() != null && x < this.getData()) return this.getLeft().find(x);
        else if (this.getRight() != null && x > this.getData()) return this.getRight().find(x);
        return null;
    }

    public BinaryTree find3(final int x) {
        if (this.getData() == x) return this;
        else if (this.getLeft() == null && this.getRight() == null) return null;
        else if (x < this.getData()) return this.getLeft() != null ? this.getLeft().find(x) : null;
        else if (x > this.getData()) return this.getRight() != null ? this.getRight().find(x) : null;

        throw new IllegalArgumentException();
    }


    public int maxLevelSum() {
        final Queue<BinaryTree> queue = new Queue<>();
        queue.enqueue(this);
        int maxLevelSum = Integer.MIN_VALUE;
        int currLevelSum = 0;
        int currLevel = 0;

        while (!queue.isEmpty()) {
            final BinaryTree curr = queue.dequeue();
            if (curr.left != null) queue.enqueue(curr.left);
            if (curr.right != null) queue.enqueue(curr.right);

            int currLevelCount = 0;
            for (BinaryTree iter = curr; iter != this; iter = iter.parent, ++currLevelCount) ;

            if (currLevelCount > currLevel) {
                ++currLevel;
                maxLevelSum = Math.max(maxLevelSum, currLevelSum);
                currLevelSum = curr.data;
            } else {
                currLevelSum += curr.data;
            }
        }

        return maxLevelSum;
    }

    @Override
    public String toString() {
        final String left = this.getLeft() != null ? String.valueOf(this.getLeft().getData()) : "null";
        final String right = this.getRight() != null ? String.valueOf(this.getRight().getData()) : "null";
        final String parent = this.getParent() != null ? String.valueOf(this.getParent().getData()) : "null";
        return "(" + this.getData() + ", " + left + ", " + right + ", " + parent + ")";
    }

    @Override
    public String toDot() {
        final String arrow = " -> ";
        final String newLine = "\n";
        final StringBuilder nodes = new StringBuilder();
        final StringBuilder connections = new StringBuilder();

        final Stack<BinaryTree> stack = new Stack<>();
        stack.push(this);
        while (!stack.isEmpty()) {
            final BinaryTree curr = stack.pop();
            if (curr.getLeft() != null) stack.push(curr.getLeft());
            if (curr.getRight() != null) stack.push(curr.getRight());

            nodes.append(curr.getData() + "\n");
            if (curr.getLeft() != null)
                connections.append(curr.getData()).append(arrow).append(curr.getLeft().getData()).append(newLine);
            if (curr.getRight() != null)
                connections.append(curr.getData()).append(arrow).append(curr.getRight().getData()).append(newLine);
        }

        return "digraph BinaryTree {\n" + nodes + newLine + connections + "}";
    }

}
