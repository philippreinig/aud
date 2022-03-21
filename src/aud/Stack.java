package aud;

import aud.adt.AbstractStack;

import java.util.NoSuchElementException;

/**
 * Implementation of a stack based on {@link aud.Vector}.
 */
public class Stack<T> extends AbstractStack<T> {
    Vector<T> data_;

    public Stack() {
      this.data_ = new Vector<T>();
    }

    @Override
    public boolean isEmpty() {
        return this.data_.empty();
    }

    @Override
    public T top() throws NoSuchElementException {
        if (this.data_.empty())
            throw new NoSuchElementException();
        return this.data_.back();
    }

    @Override
    public T pop() throws NoSuchElementException {
        if (this.data_.empty())
            throw new NoSuchElementException();
        final T obj = this.data_.back();
      this.data_.pop_back();
        return obj;
    }

    @Override
    public void push(final T x) {
      this.data_.push_back(x);
    }
}
