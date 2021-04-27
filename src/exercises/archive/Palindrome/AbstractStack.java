package exercises.archive.Palindrome;

import java.util.NoSuchElementException;

/**
 * Interface for an ADT stack.
 */
public abstract class AbstractStack<T> {

    /**
     * create empty stack
     */
    protected AbstractStack() {
    }

    /**
     * Is stack empty?
     */
    public abstract boolean isEmpty();

    /**
     * Get stack top.
     * Requires <code>!is_empty()</code>.
     *
     * @return top
     * @throws NoSuchElementException
     */
    public abstract T top();

    /**
     * Pop element from stack.
     * Requires <code>!is_empty()</code>.
     *
     * @return removed element
     * @throws NoSuchElementException
     */
    public abstract T pop();

    /**
     * Push x onto stack.
     *
     * @param x new element
     */
    public abstract void push(T x);

    /**
     * Get string representation <tt>"|a|b|c"</tt>.
     * (Here, <tt>"c"</tt> is stack top. <tt>"|"</tt> denotes empty
     * stack.)
     */
    @Override
    public String toString() {
        if (!isEmpty()) {
            T x = pop();
            String bottom = toString();
            push(x);
            return (bottom + (bottom.length() > 1 ? "|" : "")) + x;
        } else
            return "|";
    }
}
