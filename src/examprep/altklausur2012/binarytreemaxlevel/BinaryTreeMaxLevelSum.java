package examprep.altklausur2012.binarytreemaxlevel;

import aud.BinaryTree;
import aud.Stack;
import aud.util.DotViewer;
import aud.util.ParseTree;
import exam.TreeToString;

public class BinaryTreeMaxLevelSum {

    public static <T> int getLevel(final BinaryTree<T> bt) {
        BinaryTree<T> next = bt;
        int i = 1;
        while (next.getParent() != null) {
            next = next.getParent();
            ++i;
        }
        return i;
    }

    public static int max(final int a, final int b) {
        return Math.max(a, b);
    }

    public static <T> int getHeight(final BinaryTree<T> bt) {
        if (bt.getLeft() == null && bt.getRight() == null) {
            System.out.println("height of " + bt.getData() + " is " + 1);
            return 1;
        }

        final int heightLeftChild = getHeight(bt.getLeft());
        final int heightRightChild = getHeight(bt.getRight());

        final int height = max(heightLeftChild, heightRightChild) + 1;

        System.out.println("height left " + heightLeftChild + ", " + heightRightChild + " -> height of " + bt.getData() + " is " + height);

        return height;
    }

    public static int getMaxFromIntArray(final int[] array) {
        int max = Integer.MIN_VALUE;
        for (final Integer i : array) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    public static int maxLevelSum(final BinaryTree<Integer> bt) {
        final Stack<BinaryTree<Integer>> stack = new Stack<>();
        final int[] levelSums = new int[getHeight(bt) + 1];
        stack.push(bt);
        while (!stack.is_empty()) {
            final BinaryTree<Integer> next = stack.pop();
            if (next.getLeft() != null) {
                stack.push(next.getLeft());
            }
            if (next.getRight() != null) {
                stack.push(next.getRight());
            }
            final int levelOfNext = getLevel(next);
            levelSums[levelOfNext] += next.getData();
        }
        return getMaxFromIntArray(levelSums);
    }

    public static void main(final String[] args) {

        final BinaryTree<Integer> tree = new BinaryTree<>(0);
        tree.setLeft(new BinaryTree<>(1));
        tree.getLeft().setLeft(new BinaryTree<>(2));
        tree.getLeft().setRight(new BinaryTree<>(3));

        tree.setRight(new BinaryTree<>(17));
        tree.getRight().setLeft(new BinaryTree<>(5));
        tree.getRight().setRight(new BinaryTree<>(6));

        DotViewer.displayWindow(tree.toDot(), null);
        System.out.println(maxLevelSum(tree));

    }
}
