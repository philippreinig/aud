package aud.example;

import aud.BinaryTree;

/**
 * example: transform recursive preoder traversal to iterative algorithm<p>
 * <p>
 * We use {@link VerboseStack} to show that {@link #recursive_traversal}
 * and {@link #iterative_traversal} effectively do the same.
 * <p>
 * In addition, a {@link level_order_traversal} is obtained by replacing
 * the stack by a {@link VerboseQueue}.
 *
 * @see BinaryTree
 * @see BinaryTreeTraversal
 */
public class IterativePreorderTraversal {

    /**
     * recursive algorithm
     */
    public static void recursive_traversal(final BinaryTree<String> node, final int level) {
        indent(level);
        System.err.println("enter with node=" + node);
        if (node != null) {
            System.out.println("output " + node);
            recursive_traversal(node.getLeft(), level + 1);
            recursive_traversal(node.getRight(), level + 1);
        }
        indent(level);
        System.err.println("leave with node=" + node);
    }

    /**
     * iterative algorithm
     */
    public static void iterative_traversal(final BinaryTree<String> root) {
        final VerboseStack<BinaryTree<String>> stack =
                new VerboseStack<BinaryTree<String>>();
        stack.push(root);   // initialize

        while (!stack.isEmpty()) {
            final BinaryTree<String> node = stack.pop();
            if (node != null) {
                System.out.println("output " + node);
                stack.push(node.getRight()); // mind order: right first
                stack.push(node.getLeft());
            }
        }
    }

    /**
     * implement level-order traversal
     */
    public static void level_order_traversal(final BinaryTree<String> root) {
        final VerboseQueue<BinaryTree<String>> queue =
                new VerboseQueue<BinaryTree<String>>();
        queue.enqueue(root);   // initialize

        while (!queue.isEmpty()) {
            final BinaryTree<String> node = queue.dequeue();
            if (node != null) {
                System.out.println("output " + node);
                queue.enqueue(node.getLeft()); // mind order: left first
                queue.enqueue(node.getRight());
            }
        }
    }


    /**
     * generate some tree
     */
    public static BinaryTree<String> exampleTree() {
        final BinaryTree<String> root = new BinaryTree<String>
                ("A",
                        new BinaryTree<String>("B",
                                new BinaryTree<String>("C"),
                                new BinaryTree<String>("D")),
                        new BinaryTree<String>("E",
                                new BinaryTree<String>("F"),
                                new BinaryTree<String>("G",
                                        new BinaryTree<String>("H",
                                                new BinaryTree<String>("I",
                                                        new BinaryTree<String>("J"), null),
                                                new BinaryTree<String>("K")
                                        ),
                                        null
                                )
                        )
                );
        return root;
    }

    /**
     * indent output on {@code System.err}
     */
    public static void indent(final int n) {
        for (int i = 0; i < n; ++i)
            System.err.print(" ");
    }

    public static void main(final String[] args) {
        final BinaryTree<String> tree = exampleTree();

        System.out.println("recursive traversal:");
        recursive_traversal(tree, 0);
        System.out.println();

        System.out.println("iterative traversal:");
        iterative_traversal(tree);
        System.out.println();

        System.out.println("level order traversal:");
        level_order_traversal(tree);
    }
}
