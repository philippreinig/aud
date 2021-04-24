package Übungsaufgaben.Current;

import aud.DList;


public class MyDList<T> extends DList<T> {
    //-----------------------------------------------------------------//
    public MyDList() {
        super();
    }

    //-----------------------------------------------------------------//
    public static void main(String[] args) {
        // TODO: test your code with appropriate examples
    }

    //-----------------------------------------------------------------//
    public void append(MyDList<T> li) {
        for (T element : li) {
            this.push_back(element);
        }
    }

    //-----------------------------------------------------------------//
    public void insert(int n, MyDList<T> li) {
        for (int i = 0; i < li.size(); i++) {
        }
    }
}
