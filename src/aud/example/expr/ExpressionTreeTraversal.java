package aud.example.expr;

import aud.Queue;
import aud.util.DotViewer;
import aud.util.SimpleDecorator;
import aud.util.SingleStepper;

/**
 * example: visualize expression tree traversal
 */
public class ExpressionTreeTraversal extends SingleStepper {

    protected ExpressionTree tree_ = null;
    protected DotViewer viewer_ = DotViewer.displayWindow
            ((String) null, "aud.example.expr.ExpressionTreeTraversal");
    protected SimpleDecorator decorator;

    /**
     * create traversal application for {@code tree}
     */
    public ExpressionTreeTraversal(final ExpressionTree tree) {
        super("aud.example.expr.ExpressionTreeTraversal");
      this.tree_ = tree;
      this.decorator = (SimpleDecorator) this.tree_.getDecorator(); // same per tree
    }

    public static void main(final String[] args) {

        if (args.length < 1 || args.length > 2) {
            System.err.println
                    ("usage: java aud.example.expr.ExpressionTreeTraversal expr [mode]\n" +
                            "- expr: an expression, e.g., \"2*(3+4)\" " +
                            "(use quotes \"...\" on command line!)\n" +
                            "- mode: preorder | inorder | postorder | levelorder");
            System.exit(-1);
        }

        final ExpressionTree tree = new ExpressionParser2().parse(args[0]);
        final ExpressionTreeTraversal app = new ExpressionTreeTraversal(tree);

        app.traverse(args.length > 1 ? args[1] : "preorder");

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
    protected void output(final ExpressionTree node) {
      this.decorator.markNode(node);
      this.decorator.highlightNode(node);
      this.decorator.setGraphLabel(this.decorator.getGraphLabel() + node.getData() + " ");
      this.halt("output " + node.getData() + " [" + node + "]");
    }

    /**
     * arrived {@code node} for first time (for visualization)
     */
    protected void see(final ExpressionTree node) {
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
    protected void preorder(final ExpressionTree node) {
        if (node != null) {
          this.see(node);

          this.output(node);
          this.preorder((ExpressionTree) node.getLeft());
          this.preorder((ExpressionTree) node.getRight());
        }
    }

    /**
     * recursive inorder traversal
     */
    protected void inorder(final ExpressionTree node) {
        if (node != null) {
          this.see(node);

          this.inorder((ExpressionTree) node.getLeft());
          this.output(node);
          this.inorder((ExpressionTree) node.getRight());
        }
    }

    /**
     * recursive postorder traversal
     */
    protected void postorder(final ExpressionTree node) {
        if (node != null) {
          this.see(node);

          this.postorder((ExpressionTree) node.getLeft());
          this.postorder((ExpressionTree) node.getRight());
          this.output(node);
        }
    }

    /**
     * level order traversdal
     */
    protected void levelorder(final ExpressionTree root) {
        final Queue<ExpressionTree> queue = new Queue<ExpressionTree>();
        queue.enqueue(null);     // marker: next level
        queue.enqueue(root);
        int level = 0;             // keep track of/count levels
        while (!queue.isEmpty()) {
            final ExpressionTree node = queue.dequeue();

            if (node == null) {
              this.decorator.highlightNode(null);
              this.halt("enter next level " + (level++));
                if (!queue.isEmpty())
                    queue.enqueue(null); // marker: next level
            } else {
              this.see(node);

                if (node.getLeft() != null)
                    queue.enqueue((ExpressionTree) node.getLeft());
                if (node.getRight() != null)
                    queue.enqueue((ExpressionTree) node.getRight());
              this.output(node);
            }
        }
    }
}
