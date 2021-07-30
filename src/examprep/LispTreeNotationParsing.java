package examprep;

import aud.BinaryTree;
import aud.util.DotViewer;
import exam.TreeToString;

public class LispTreeNotationParsing {
    public static void main(String[] args) {
        BinaryTree<String> bt = new BinaryTree<>("H");
        bt.setLeft(new BinaryTree<>("C"));
        bt.setRight(new BinaryTree<>("J"));
        bt.getLeft().setLeft(new BinaryTree<>("B"));
        bt.getLeft().getLeft().setLeft(new BinaryTree<>("A"));
        bt.getLeft().setRight(new BinaryTree<>("F"));
        bt.getLeft().getRight().setLeft(new BinaryTree<>("A"));
        bt.getLeft().getRight().setLeft(new BinaryTree<>("E"));
        bt.getLeft().getRight().setRight(new BinaryTree<>("G"));
        bt.getLeft().getRight().getLeft().setLeft(new BinaryTree<>("D"));

        bt.getRight().setRight(new BinaryTree<>("K"));
        bt.getRight().setLeft(new BinaryTree<>("I"));
//        bt.getRight().getRight().setRight(new BinaryTree<>("I"));
//        bt.getRight().getLeft().setLeft(new BinaryTree<>("D"));
//        bt.getRight().getLeft().setRight(new BinaryTree<>("F"));

        System.out.println(TreeToString.toTreeNotation(bt));
        DotViewer.displayWindow(bt, null);

//        ParseTree.main(new String[]{"(C (A () B9) (E (D (F () ()))", "--show"});
    }
}
