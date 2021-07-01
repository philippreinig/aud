package aud.adt;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Interface for an ADT priority queue.<p>
 * {@link #front} provdes the <em>smallest</em> entry (w.r.t. to a user
 * defined {@code Comparator})
 */
public abstract class AbstractPriorityQueue<T> {

    protected Comparator<T> cmp_ = null;

    /**
     * create empty PQ and use {@code cmp_} for comparison of priorities
     */
    protected AbstractPriorityQueue(java.util.Comparator<T> cmp) {
        this();
        cmp_ = cmp;
    }

    /**
     * create empty PQ
     */
    protected AbstractPriorityQueue() {
    }

    /**
     * test for {@code a<b},
     * uses {@code Comparator} if one was provided or {@code Comparable} else.
     */
    @SuppressWarnings("unchecked")
    protected boolean less(T a, T b) {
        return (cmp_ != null) ?
                (cmp_.compare(a, b) < 0) : (((Comparable<T>) a).compareTo(b) < 0);
    }

    /**
     * Is PQ empty?
     */
    public abstract boolean is_empty();

    /**
     * Get <em>minimal</em> element.
     * Requires <code>!is_empty()</code>.
     *
     * @return top
     * @throws NoSuchElementException
     */
    public abstract T front();

    /**
     * Pop <em>minimal</em> element from PQ.
     * Requires <code>!is_empty()</code>.
     *
     * @return removed (minimal) element
     * @throws NoSuchElementException
     */
    public abstract T pop();

    /**
     * Push x into PQ.
     *
     * @param x new element
     */
    public abstract void push(T x);
}
