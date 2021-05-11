package exercises.current;

import aud.*;               //to use BinaryTree<T>
import aud.util.*;          //to use DotViewer, SingleStepper
import java.util.Iterator;

public class IntBinTree extends BinaryTree<Integer> {
    //---------------------------------------------------------------//
    public IntBinTree(int data) {
        super(data);
    }

    //---------------------------------------------------------------//
    public IntBinTree(int data, IntBinTree left, IntBinTree right) {
        super(data, left, right);
    }

    //---------------------------------------------------------------//
    public int height() {
        int heightLeft = this.getLeft() != null ?  this.getLeft().height() : 0;
        int heightRight = this.getRight() != null ? this.getRight().height() : 0;

        int maxHeight = Math.max(heightLeft, heightRight);
        return maxHeight + 1;
    }
    
    private int getSum(){
        int sum = 0;
        for (BinaryTree<Integer> element : this.inorder()){
            sum += element.getData();
        }
        return sum;
    }

    //---------------------------------------------------------------//
    public int maxSum() {
        int sumLeft = this.getLeft() != null ? this.getLeft().getSum(): 0;
        int sumRight = this.getRight() != null ? this.getRight().getSum() : 0;
        return Math.max(sumLeft, sumRight);
    }

    //---------------------------------------------------------------//
    public int maxPath() {
        if (this.getLeft() == null && this.getRight() == null){
            return this.getData();
        }
        if(this.getLeft() == null){
            return this.getData() + this.getRight().maxPath();
        }
        if(this.getRight() == null){
            return this.getData() + this.getLeft().maxPath();
        }
        return this.getData() + Math.max(this.getLeft().maxPath(), this.getRight().maxPath());
    }

    @Override
    public IntBinTree getRight(){
        return (IntBinTree) super.getRight();
    }

    @Override
    public IntBinTree getLeft(){
        return (IntBinTree) super.getLeft();
    }

    //---------------------------------------------------------------//
    public static void main(String[] args) {
            IntBinTree rightChild21 = new IntBinTree(-11, null, null);
            IntBinTree leftChild21 = new IntBinTree(6, null, null);
            IntBinTree leftChild1 = new IntBinTree(4, leftChild21, rightChild21);
            IntBinTree leftChild22 = new IntBinTree(5);
            IntBinTree rightChild22 = new IntBinTree(7);
            IntBinTree rightChild1 = new IntBinTree(1, leftChild22, rightChild22);
            IntBinTree tree = new IntBinTree(-8, leftChild1, rightChild1);

            for(BinaryTree<Integer> t : tree.inorder()){
                System.out.println(t.getData() + " height: " + ((IntBinTree) t).height());
                System.out.println(t.getData() + " maxSum: " + ((IntBinTree) t).maxSum());
                System.out.println(t.getData() + " maxPath: " + ((IntBinTree) t).maxPath());
        }

            DotViewer.displayWindow(tree.toDot(), "IntBinTree");
    }
}