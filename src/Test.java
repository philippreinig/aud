import aud.util.DotViewer;
import exercises.archive.BuildBinTree;
import exercises.archive.IntBinTree2;

public class Test {
    public static void main(String[] args) {
        IntBinTree2 rightChild21 = new IntBinTree2(-11, null, null);
        IntBinTree2 leftChild21 = new IntBinTree2(6, null, null);
        IntBinTree2 leftChild1 = new IntBinTree2(4, leftChild21, rightChild21);
        IntBinTree2 leftChild22 = new IntBinTree2(5, null, null);
        IntBinTree2 rightChild22 = new IntBinTree2(7, null, null);
        IntBinTree2 rightChild1 = new IntBinTree2(1, leftChild22, rightChild22);
        IntBinTree2 tree = new IntBinTree2(-8, leftChild1, rightChild1);

        BuildBinTree<Integer> bbb = new BuildBinTree<>();
        bbb.printAll(tree);


        DotViewer.displayWindow(tree, "BinaryTree").setExitOnClose();
    }
}
