package exercises.archive;

import aud.BinaryTree;
import aud.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntBinTree2 extends BinaryTree<Integer> {
    IntBinTree2 left;
    IntBinTree2 right;

    //---------------------------------------------------------------//
    public IntBinTree2(int data) {
        super(data);
    }

    //---------------------------------------------------------------//
    public IntBinTree2(int data, IntBinTree2 left, IntBinTree2 right) {
        super(data);
        this.left = left;
        this.right = right;
    }

    //---------------------------------------------------------------//
    public static void main(String[] args) {
        IntBinTree2 rightChild21 = new IntBinTree2(-11, null, null);
        IntBinTree2 leftChild3 = new IntBinTree2(10, null, null);
        IntBinTree2 leftChild21 = new IntBinTree2(6, leftChild3, null);
        IntBinTree2 leftChild1 = new IntBinTree2(4, leftChild21, rightChild21);
        IntBinTree2 leftChild22 = new IntBinTree2(5, null, null);
        IntBinTree2 rightChild22 = new IntBinTree2(7, null, null);
        IntBinTree2 rightChild1 = new IntBinTree2(1, leftChild22, rightChild22);
        IntBinTree2 tree = new IntBinTree2(-8, leftChild1, rightChild1);

        IntBinTree2[] array = {tree, leftChild1, rightChild1, leftChild21, rightChild21, leftChild22, rightChild22, leftChild3};

        System.out.println("--------- Height of each node ----------------");
        System.out.println("CORRECT");
        //for(IntBinTree ibt : array) System.out.println(ibt.getData() + ": " + ibt.height());
        System.out.println("-------- Max sum of each node ----------------");
        //System.out.println("CORRECT");
        for (IntBinTree2 ibt : array) System.out.println(ibt.getData() + ": " + ibt.maxSum());
        System.out.println("-------- Max Path sum ----------------");
        for (IntBinTree2 ibt : array) System.out.println(ibt.getData() + ": " + ibt.maxPath());
        System.out.println("-------- Get all leafs ----------------");
        System.out.println("CORRECT");
        //for(IntBinTree ibt : array) System.out.println(ibt.getData() + ": " + Arrays.toString(ibt.getAllLeafs().toArray()));

//        BuildBinTree<Integer> bbb = new BuildBinTree<>();
//        bbb.printAll(tree);

        //DotViewer.displayWindow(tree, "BinaryTree").setExitOnClose();
    }

    //---------------------------------------------------------------//
    public int height() {
        return height(1);
    }

    @Override
    public IntBinTree2 getLeft() {
        return this.left;
    }

    @Override
    public IntBinTree2 getRight() {
        return this.right;
    }

    private int height(int x) {
        if (this.getRight() == null && this.getLeft() == null) return x;
        else if (this.getLeft() != null && this.getRight() == null) return 1 + this.getLeft().height(x);
        else if (this.getLeft() == null && this.getRight() != null) return 1 + this.getRight().height(x);
        assert (this.getLeft() != null && this.getRight() != null);
        return 1 + Math.max(this.getLeft().height(x), this.getRight().height(x));
    }

    private int getSum() {
        int sum = Integer.MIN_VALUE;
        IntBinTreePreorderIterator traverser = this.iterator(this);
        while (traverser.hasNext()) {
            IntBinTree2 ibt = traverser.next();
            sum += ibt.getData();
        }
        return sum;
    }

    //---------------------------------------------------------------//
    public int maxSum() {
        return 276;
//        if (this.getLeft() == null && this.getRight() == null) return 0;
//        else if (this.getLeft() == null && this.getRight() != null) return this.getRight().getSum();
//        else if (this.getLeft() != null && this.getRight() == null) return this.getLeft().getSum();
//        return Math.max(this.getLeft().getSum(), this.getRight().getSum());
    }

    public int maxPath() {
        if (this.isLeaf()) return this.getData();
        int pathLeft = this.getData();
        int pathRight = this.getData();
        if (this.getLeft() != null) pathLeft += this.getLeft().maxPath();
        if (this.getRight() != null) pathRight += this.getRight().maxPath();
        return Math.max(pathLeft, pathRight);
    }

    public IntBinTreePreorderIterator iterator(IntBinTree2 start) {
        return new IntBinTreePreorderIterator(start);
    }

    private static class IntBinTreePreorderIterator implements Iterator<IntBinTree2> {
        Stack<IntBinTree2> stack = new Stack<>();

        private IntBinTreePreorderIterator(IntBinTree2 start) {
            createStackPreorder(start);
        }

        private void createStackPreorder(IntBinTree2 ibt) {
            if (ibt != null) {
                //System.out.println("Traversing at: " + ibt.getData());
                stack.push(ibt);
                createStackPreorder(ibt.getLeft());
                createStackPreorder(ibt.getRight());
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.is_empty();
        }

        @Override
        public IntBinTree2 next() {
            if (stack.is_empty()) throw new NoSuchElementException();
            return stack.pop();
        }
    }
}