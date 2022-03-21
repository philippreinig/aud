package aud;

import java.util.Iterator;

/**
 * Provide traversals of binary trees.<p>
 * <p>
 * Defines iterators and classes generating iterators
 * for various traversal orders.
 *
 * @see aud.BinaryTree
 * @see aud.example.BinaryTreeTraversal example.BinaryTreeTraversal
 * (recursive traversion demo)
 */
public class BinaryTreeTraversal<T> {

    /**
     * return instance of generator
     *
     * @see BinaryTree#preorder
     */
    public Preorder preorder(final BinaryTree<T> tree) {
        return new Preorder(tree);
    }

    /**
     * return instance of generator
     *
     * @see BinaryTree#inorder
     */
    public Inorder inorder(final BinaryTree<T> tree) {
        return new Inorder(tree);
    }

    /**
     * return instance of generator
     *
     * @see BinaryTree#postorder
     */
    public Postorder postorder(final BinaryTree<T> tree) {
        return new Postorder(tree);
    }

    /**
     * return instance of generator
     *
     * @see BinaryTree#levelorder
     */
    public Levelorder levelorder(final BinaryTree<T> tree) {
        return new Levelorder(tree);
    }

    /**
     * Base class for traversal.
     * An instance creates iterators for a particular traversal.
     */
    public abstract class Traversal
            implements Iterable<BinaryTree<T>> {
        BinaryTree<T> tree_;

        Traversal(final BinaryTree<T> tree) {
          this.tree_ = tree;
        }

        @Override
        public String toString() {
            String s = "";
            for (final BinaryTree<T> node : this) {
                s += node.getData().toString() + ",";
            }
            return s.substring(0, s.length() - 1);
        }
    }

    /**
     * base class for stack-based pre-/in-/postorder traversal
     */
    public abstract class RecursiveTraversalIterator
            implements Iterator<BinaryTree<T>> {

        Stack<BinaryTree<T>> stack_ = new Stack<BinaryTree<T>>();

        RecursiveTraversalIterator(final BinaryTree<T> tree) {
          this.stack_.push(tree);
        }

        @Override
        public boolean hasNext() {
            return !this.stack_.isEmpty();
        }

        /**
         * @throws UnsupportedOperationException (not implemented)
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * preorder iterator for {@link BinaryTree}
     */
    public class PreorderIterator
            extends RecursiveTraversalIterator {
        PreorderIterator(final BinaryTree<T> tree) {
            super(tree);
        }

        @Override
        public BinaryTree<T> next() {
            final BinaryTree<T> node = this.stack_.pop(); // may throw!
            if (node.getRight() != null) this.stack_.push(node.getRight());
            if (node.getLeft() != null) this.stack_.push(node.getLeft());
            return node;
        }
    }

    /**
     * helper: generates {@link PreorderIterator}
     */
    public class Preorder
            extends Traversal implements Iterable<BinaryTree<T>> {
        Preorder(final BinaryTree<T> tree) {
            super(tree);
        }

        @Override
        public Iterator<BinaryTree<T>> iterator() {
            return new PreorderIterator(this.tree_);
        }
    }

    /**
     * inorder iterator for {@link BinaryTree}
     */
    public class InorderIterator
            extends RecursiveTraversalIterator {
        InorderIterator(final BinaryTree<T> tree) {
            super(tree);
          this.descendLeft();
        }

        private void descendLeft() {
            BinaryTree<T> node = this.stack_.top();
            for (node = node.getLeft(); node != null; node = node.getLeft()) this.stack_.push(node);
        }

        @Override
        public BinaryTree<T> next() {
            final BinaryTree<T> node = this.stack_.pop();
            if (node.getRight() != null) {
              this.stack_.push(node.getRight());
              this.descendLeft();
            }

            return node;
        }
    }

    /**
     * helper: generates {@link InorderIterator}
     */
    public class Inorder
            extends Traversal implements Iterable<BinaryTree<T>> {
        Inorder(final BinaryTree<T> tree) {
            super(tree);
        }

        @Override
        public Iterator<BinaryTree<T>> iterator() {
            return new InorderIterator(this.tree_);
        }
    }

    /**
     * postorder iterator for {@link BinaryTree}
     */
    public class PostorderIterator
            extends RecursiveTraversalIterator {
        final static int LEFT = 0;
        final static int RIGHT = 1;
        final static int OUTPUT = 2;

    /* Idea:

       We keep a second stack (of same size), which keeps track of the
       current state.  Note: One single stack would be enough, but
       then we would have to worry about types (node or state).

       The state tells us, "where we are". Have alook at the recursive
       version. Then the state is defined as follows

       visit(node)
           // state == LEFT
         visit(node.left)
	   // state == RIGHT
         visit(node.right)
	   // state == OUTPUT
         output(node)
     */

        Stack<Integer> state_ = new Stack<Integer>();

        PostorderIterator(final BinaryTree<T> tree) {
            super(tree);
          this.state_.push(LEFT);
        }

        private void descendLeft() {
            BinaryTree<T> node = this.stack_.top();
            for (node = node.getLeft(); node != null; node = node.getLeft()) {
              this.stack_.push(node);
              this.state_.push(RIGHT);
            }
        }

        @Override
        public boolean hasNext() {
            assert (this.stack_.isEmpty() == this.state_.isEmpty()); // additional check
            return super.hasNext();
        }

        @Override
        public BinaryTree<T> next() {
            int state;
            while ((state = this.state_.pop()) != OUTPUT) {
                if (state == LEFT) {
                    // stack_.top() remains
                  this.state_.push(RIGHT);

                  this.descendLeft();
                } else {
                    // stack_.top() remains
                  this.state_.push(OUTPUT);

                    final BinaryTree<T> node = this.stack_.top();
                    if (node.getRight() != null) {
                      this.stack_.push(node.getRight());
                      this.state_.push(LEFT);
                    }
                }
            }
            return this.stack_.pop(); // state == OUTPUT
        }
    }

    /**
     * helper: generates {@link PostorderIterator}
     */
    public class Postorder
            extends Traversal implements Iterable<BinaryTree<T>> {
        Postorder(final BinaryTree<T> tree) {
            super(tree);
        }

        @Override
        public Iterator<BinaryTree<T>> iterator() {
            return new PostorderIterator(this.tree_);
        }
    }

    /**
     * level-order iterator for {@link BinaryTree}
     */
    public class LevelorderIterator implements Iterator<BinaryTree<T>> {

        Queue<BinaryTree<T>> queue_ = new Queue<BinaryTree<T>>();

        LevelorderIterator(final BinaryTree<T> tree) {
          this.queue_.enqueue(tree);
        }

        @Override
        public boolean hasNext() {
            return !this.queue_.isEmpty();
        }

        /**
         * @throws UnsupportedOperationException (not implemented)
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public BinaryTree<T> next() {
            final BinaryTree<T> node = this.queue_.dequeue(); // may throw!
            if (node.getLeft() != null) this.queue_.enqueue(node.getLeft());
            if (node.getRight() != null) this.queue_.enqueue(node.getRight());
            return node;
        }
    }

    /**
     * helper: generates {@link LevelorderIterator}
     */
    public class Levelorder
            extends Traversal implements Iterable<BinaryTree<T>> {
        Levelorder(final BinaryTree<T> tree) {
            super(tree);
        }

        @Override
        public Iterator<BinaryTree<T>> iterator() {
            return new LevelorderIterator(this.tree_);
        }
    }

}
