package aud;

import aud.adt.AbstractQueue;

import java.util.NoSuchElementException;

/**
 * Implementation of AbstractQueue as a (dynamically resized)
 * circular buffer based on array.
 */
//@<queue:class
public class Queue<T> extends AbstractQueue<T> {

    static final int DEFAULT_SIZE = 16;
    T[] data_;
    int head_ = 0;
    //@>queue:class
    int tail_ = 0;

    /**
     * create empty queue
     */
    public Queue() {
        this(DEFAULT_SIZE);
    }

    /**
     * create empty queue and reserve storage
     */
    @SuppressWarnings("unchecked")
    //@<queue:ctor
    public Queue(final int capacity) {
      this.data_ = (T[]) new Object[capacity];
      this.head_ = this.tail_ = 0;
    }
    //@>queue:ctor

    @Override
    //@<queue:empty
    public boolean isEmpty() {
        return this.head_ == this.tail_;
    }
    //@>queue:empty

    @Override
    //@<queue:front
    public T front() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return this.data_[this.head_];
    }
    //@>queue:front

    //@<queue:dequeue
    @Override
    public T dequeue() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        final T obj = this.data_[this.head_];
      this.data_[this.head_] = null; // "free" entry (for GC)
      this.head_ = (this.head_ + 1) % this.data_.length;
        return obj;
    }
    //@>queue:dequeue

    @Override
    //@<queue:enqueue1
    public void enqueue(final T x) {
        final int newtail = (this.tail_ + 1) % this.data_.length;

        if (newtail != this.head_) {
            // no "overrun" we just advance an index.
          this.data_[this.tail_] = x;
          this.tail_ = newtail;
        } else {
            //@>queue:enqueue1
            //System.err.println("enlarge buffer of size "+data_.length);

            // detected "overrun": the new head "hits" tail --
            // This means the alloacted buffer data is too small!

            // get larger buffer q
            final Queue<T> q = new Queue<T>(this.data_.length * 2);

            // copy data into q
            while (!this.isEmpty())
                q.enqueue(this.dequeue());
            q.enqueue(x);

            // "steal" state of temporary object q
          this.data_ = q.data_;
          this.head_ = q.head_;
          this.tail_ = q.tail_;
            assert (this.head_ == 0 && this.tail_ < this.data_.length);
        }
    }

    /**
     * Get string representation.
     *
     * <b>Impementation note:</b><p>
     * <p>
     * This method reads out the queue in <em>reverse</em> order, i.e.,
     * we are "going backwards" and shift an index by -1 instead of +1
     * modulo array length.<p>
     * <p>
     * The Java expression a%b computes the remainder of an integer
     * division and is <em>differs</em> from the mathematical
     * definition of a mod b for negative operands!<p>
     * <p>
     * The modulo operation is defined as
     *
     * <center><tt>a mod b := b-floor(a/b)*b</tt></center>
     * <p>
     * where tt>floor(x)</tt> rounds to the greatest integer that is
     * less than or equal to x (i.e., "towards negative infinity").<p>
     * <p>
     * This is what we want, because
     *
     * <center><tt>a mod b = (a+k*b) mod b</tt> for any integer <tt>k</tt>
     * (regardless of sign)</center>
     * <p>
     * For example consider an array of size 3 then position -1 should
     * refer to the <em>last</em> entry
     *
     * <center><tt>-1 mod 3 = -1+3 mod 3 = 2 mod 3 = 2</tt></center>
     * <p>
     * Java (and similarly C/C++) defines the remainder operator
     * differently such that the sign is treated "symmetrically" (not
     * rounding towards negative infinity but towards 0):<p>
     *
     * <center><tt>a%b = (-a%b) for b>=0</tt></center>
     * <p>
     * For the example, we get
     *
     * <center><tt>(-1)%3 = 1%3 = 1</tt></center>
     * <p>
     * which is <em>not</em> what we want as index into the buffer!<p>
     * <p>
     * We avoid this issue by adding <tt>-1+data_.length</tt> instead
     * of <tt>-1</tt>.<p>
     *
     * <b>Summary:</b>
     * <ul>
     * <li>Computing the remainder of an integer division
     * (by an operator) is treated <em>differently in different
     * programming languages</em>.</li>
     * <li>The same is true for the (rounding mode of the) integer
     * division itself. The reason for the "non-mathematical" definition
     * is probably the behavior of the machine instructions (like <tt>idiv<</tt>
     * on Itel CPUs).</li>
     * <li>Differences show up for <em>negative operands</em> And
     * they can lead to subtle bugs!</li>
     * <li><em>To be safe, make sure that operands are nonegative!</em> </li>
     * </ul>
     */
    @Override
    public String toString() {
        if (this.isEmpty())
            return "<";

        String s = "";

        int t = (this.tail_ + this.data_.length - 1) % this.data_.length;
        for (; ; ) {
            s = this.data_[t] + "<" + s;
            if (t == this.head_)
                break;
            t = (t + this.data_.length - 1) % this.data_.length;
        }
        return s;
    }
}
