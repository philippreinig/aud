package exam;

import aud.BinaryTree;
import aud.util.DotViewer;

public class TreeToString {

    public static <T> String toTreeNotation(final BinaryTree<T> tree) {
        if (tree.getLeft() == null && tree.getRight() == null) {
            return tree.getData().toString();
        } else if (tree.getLeft() != null && tree.getRight() == null) {
            return "(" + tree.getData().toString() + " " + toTreeNotation(tree.getLeft()) + " ())";
        } else if (tree.getLeft() == null && tree.getRight() != null) {
            return "(" + tree.getData().toString() + " () " + toTreeNotation(tree.getRight()) + ")";
        } else {
            return "(" + tree.getData().toString() + " " + toTreeNotation(tree.getLeft()) + " " + toTreeNotation(tree.getRight()) + ")";
        }
    }

    public static void main(final String[] args) {
        final BinaryTree<Integer> tree = new BinaryTree<>(5);
        final BinaryTree<Integer> drei = new BinaryTree<>(3);
        final BinaryTree<Integer> acht = new BinaryTree<>(8);
        final BinaryTree<Integer> vier = new BinaryTree<>(4);
        final BinaryTree<Integer> sechs = new BinaryTree<>(6);
        final BinaryTree<Integer> sieben = new BinaryTree<>(7);
        final BinaryTree<Integer> neun = new BinaryTree<>(9);

        tree.setLeft(drei);
        tree.setRight(acht);
        drei.setRight(vier);
        acht.setLeft(sieben);
        acht.setRight(neun);
        sieben.setLeft(sechs);

        DotViewer.displayWindow(tree, "Baum").setExitOnClose();
        System.out.println(toTreeNotation(tree));

    }

}