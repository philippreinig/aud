package Uebungsaufgaben.Current;

import aud.DList;


public class MyDList<T> extends DList<T> {
    //-----------------------------------------------------------------//
    public MyDList() {
        super();
    }

    //-----------------------------------------------------------------//
    public static void main(String[] args) {
        MyDList<Integer> mydl1 = new MyDList<>();
        MyDList<Integer> mydl2 = new MyDList<>();
        mydl1.push_back(1);
        mydl1.push_back(2);
        mydl1.push_back(3);

        mydl2.push_back(4);
        mydl2.push_back(5);

        System.out.println(mydl1);
        System.out.println(mydl2);
        mydl1.append(mydl2);
        System.out.println(mydl1);
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
