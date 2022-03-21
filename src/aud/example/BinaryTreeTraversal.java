package aud.example;

import aud.BinaryTree;
import aud.Queue;
import aud.util.DotViewer;
import aud.util.GraphvizDecorator;
import aud.util.SimpleDecorator;
import aud.util.SingleStepper;

/**
 * example: visualize binary tree traversal
 *
 * @see aud.BinaryTree
 * @see aud.example.IterativePreorderTraversal
 * @see aud.BinaryTreeTraversal BinaryTreeTraversal (iterators)
 */
public class BinaryTreeTraversal extends SingleStepper {

    protected MyTree tree_ = null;
    protected DotViewer viewer_ = DotViewer.displayWindow
            ((String) null, "aud.example.BinaryTreeTraversal");
    protected SimpleDecorator decorator;

    /**
     * create traversal application for {@code tree}
     */
    public BinaryTreeTraversal(final MyTree tree) {
        super("aud.example.BinaryTreeTraversal");
      this.tree_ = tree;
      this.decorator = (SimpleDecorator) this.tree_.getDecorator(); // same per tree
    }

    /**
     * generate some tree
     */
    public static MyTree exampleTree() {
        final MyTree root = new MyTree
                ("A",
                        new MyTree("B",
                                new MyTree("C"),
                                new MyTree("D")),
                        new MyTree("E",
                                new MyTree("F"),
                                new MyTree("G",
                                        new MyTree("H",
                                                new MyTree("I", new MyTree("J"), null),
                                                new MyTree("K")
                                        ),
                                        null
                                )
                        )
                );
        return root;
    }

    public static void main(final String[] args) {
        final BinaryTreeTraversal app =
                new BinaryTreeTraversal(BinaryTreeTraversal.exampleTree());

        if (args.length == 0)
            app.traverse("preorder");
        else
            for (final String type : args)
                app.traverse(type);

        app.halt("QUIT");
        System.exit(0);
    }

    @Override
    protected void onHalt() {
        if (this.tree_ != null)
          this.viewer_.display(this.tree_);
    }

    /**
     * output node during traversal
     *
     * @see #traverse
     */
    protected void output(final MyTree node) {
      this.decorator.markNode(node);
      this.decorator.highlightNode(node);
      this.decorator.setGraphLabel(this.decorator.getGraphLabel() + node);
      this.halt("output " + node);
    }

    /**
     * arrived {@code node} for first time (for visualization)
     */
    protected void see(final MyTree node) {
      this.decorator.markEdge(node);
    }

    /**
     * start traversal
     *
     * @param type denotes the type of traversal "preorder",
     *             "inorder","postorder"
     * @throws RuntimeException for unknown {@code type}
     */
    public void traverse(final String type) {
      this.decorator.clear();
      this.decorator.setGraphLabel("Traversal: ");

        if (type.toLowerCase().startsWith("pre")) {
          this.halt("START preorder traversal");
          this.preorder(this.tree_);
        } else if (type.toLowerCase().startsWith("in")) {
          this.halt("START inorder traversal");
          this.inorder(this.tree_);
        } else if (type.toLowerCase().startsWith("post")) {
          this.halt("START postorder traversal");
          this.postorder(this.tree_);
        } else if (type.toLowerCase().startsWith("level")) {
          this.halt("START level-order traversal");
          this.levelorder(this.tree_);
        } else
            throw new RuntimeException("unknown traversal '" + type + "'");

      this.decorator.highlightNode(null);
      this.halt("FINISHED");
    }

    /**
     * recursive preorder traversal
     */
    protected void preorder(final MyTree node) {
        if (node != null) {
          this.see(node);

          this.output(node);
          this.preorder((MyTree) node.getLeft());
          this.preorder((MyTree) node.getRight());
        }
    }

    /**
     * recursive inorder traversal
     */
    protected void inorder(final MyTree node) {
        if (node != null) {
          this.see(node);

          this.inorder((MyTree) node.getLeft());
          this.output(node);
          this.inorder((MyTree) node.getRight());
        }
    }

    /**
     * recursive postorder traversal
     */
    protected void postorder(final MyTree node) {
        if (node != null) {
          this.see(node);

          this.postorder((MyTree) node.getLeft());
          this.postorder((MyTree) node.getRight());
          this.output(node);
        }
    }

    /**
     * level order traversdal
     */
    protected void levelorder(final MyTree root) {
        final Queue<MyTree> queue = new Queue<MyTree>();
        queue.enqueue(null);     // marker: next level
        queue.enqueue(root);
        int level = 0;             // keep track of/count levels
        while (!queue.isEmpty()) {
            final MyTree node = queue.dequeue();

            if (node == null) {
              this.halt("enter next level " + (level++));
                if (!queue.isEmpty())
                    queue.enqueue(null); // marker: next level
            } else {
              this.see(node);

                if (node.getLeft() != null)
                    queue.enqueue((MyTree) node.getLeft());
                if (node.getRight() != null)
                    queue.enqueue((MyTree) node.getRight());
              this.output(node);
            }
        }
    }

    /**
     * simple tree with decorator for visualization
     */
    static class MyTree extends BinaryTree<String> {
        static final GraphvizDecorator decorator = new SimpleDecorator();

        public MyTree(final String data) {
            super(data);
        }

        public MyTree(final String data, final MyTree left, final MyTree right) {
            super(data, left, right);
        }

        @Override
        public GraphvizDecorator getDecorator() {
            return decorator;
        }
    }
}
