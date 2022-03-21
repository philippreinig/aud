package aud;

import aud.adt.AbstractQueue;

import java.util.NoSuchElementException;

/**
 * Impementation of AbstractQueue based on doubly linked list
 * {@link DList}.
 */
//@<queuedl:class
public class QueueDL<T> extends AbstractQueue<T> {

    DList<T> data_;

    public QueueDL() {
      this.data_ = new DList<T>();
    }
//@>queuedl:class

    //@<queuedl:state
    @Override
    public boolean isEmpty() {
        return this.data_.empty();
    }

    @Override
    public T front() {
        if (this.data_.empty())
            throw new NoSuchElementException();
        return this.data_.front();
    }
    //@>queuedl:state

    //@<queuedl:manip
    @Override
    public T dequeue() {
        if (this.data_.empty())
            throw new NoSuchElementException();
        final T obj = this.data_.front();
      this.data_.pop_front();
        return obj;
    }

    @Override
    public void enqueue(final T x) {
      this.data_.push_back(x);
    }
    //@>queuedl:manip

    @Override
    public String toString() {
        if (this.isEmpty())
            return "<";

        String s = "";
        final DList<T>.ForwardIterator i = this.data_.iterator();
        while (i.hasNext()) {
            s += i.next().toString() + "<";
        }
        return s;
    }
}
