package exercises.week03;

import aud.DList;
import aud.Queue;


public class MyDList<T> extends DList<T> {

    public MyDList() {
        super();
    }

    public static void main(final String[] args) {
        final MyDList<Integer> mydl1 = new MyDList<>();
        final MyDList<Integer> mydl2 = new MyDList<>();
        mydl1.push_back(1);
        mydl1.push_back(2);
        mydl1.push_back(3);

        mydl2.push_back(4);
        mydl2.push_back(5);
        mydl2.push_back(6);
        System.out.println(mydl1);
//        System.out.println(mydl2);
        //mydl1.append(mydl2);
        mydl1.insert(1, mydl2);
        System.out.println(mydl1);
//        mydl1.append(mydl1);
//        System.out.println(mydl1);
    }

    // O(n) = n
    public void append(final MyDList<T> li) {
        final Queue<T> queue = new Queue<>();
        for (final T t : li) queue.enqueue(t);
        T next = queue.dequeue();
        while (!queue.isEmpty()) {
            this.push_back(next);
            next = queue.dequeue();
        }
        this.push_back(next);
    }

    // O(n) = n
    public void insert(final int n, final MyDList<T> li) {
        if (n < 0 || n >= this.size()) throw new IndexOutOfBoundsException();
        final Queue<T> queue = new Queue<>();
        for (final T t : li) queue.enqueue(t);
        T next = queue.dequeue();
        int i;
        for (i = 0; !queue.isEmpty(); i++) {
            super.insert(n + i, next);
            next = queue.dequeue();
        }
        super.insert(i + n, next);
    }
}

