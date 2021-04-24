package Uebungsaufgaben.Archive.Palindrome;// package aud;

// import aud.adt.AbstractStack;

import java.util.NoSuchElementException;

/**
 * Implementation of a stack based on {@link Vector}.
 */
public class Stack<T> extends AbstractStack<T> {
    Vector<T> data_;

    public Stack() {
        data_ = new Vector<>();
    }

    @Override
    public boolean isEmpty() {
        return data_.empty();
    }

    @Override
    public T top() throws NoSuchElementException {
        if (data_.empty())
            throw new NoSuchElementException();
        return data_.back();
    }

    public T pop() throws NoSuchElementException {
        if (data_.empty())
            throw new NoSuchElementException();
        T obj = data_.back();
        data_.pop_back();
        return obj;
    }

    public void push(T x) {
        data_.push_back(x);
    }
}
