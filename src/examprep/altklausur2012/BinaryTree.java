package examprep.altklausur2012;

public class BinaryTree {
    private  int data;
    private BinaryTree left;
    private BinaryTree right;
    private BinaryTree parent;

    public BinaryTree(int data){
        this(data, null, null, null);
    }

    public BinaryTree(int data, BinaryTree left, BinaryTree right, BinaryTree parent){
        this.data = data;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public int getData(){
        return this.data;
    }

    public BinaryTree getLeft() {
        return this.left;
    }

    public BinaryTree getRight(){
        return this.right;
    }

    public BinaryTree getParent() {
        return this.parent;
    }

    public BinaryTree find(int x) {
        BinaryTree next = this;
        while (next != null) {
            if (next.getData() == x) return next;
            else if (x < next.getData()) next = next.getLeft();
            else next = next.getRight();
        }

        return null;
    }

}
