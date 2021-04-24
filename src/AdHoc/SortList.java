package AdHoc;

import aud.SList;

public class SortList<T extends Comparable<T>> {
    private SList<T> list = new SList<>();

    public SortList() {
    }

    public static void main(String[] args) {
        SortList<Integer> sl = new SortList<>();
        sl.insert(2);
        sl.insert(3);
        sl.insert(1);
        System.out.println(sl.toString());
        sl.insert(4);
        sl.insert(0);
        System.out.println(sl.insert(2));
        System.out.println(sl.toString());
    }

    public boolean insert(T obj) {
        if (list.empty()) {
            list.push_front(obj);
            return true;
        }
        int counter = 0;
        for (T data : list) {
            if (obj.compareTo(data) < 0) {
                list.insert(counter, obj);
                return true;
            }
            if (obj.compareTo(data) == 0) {
                return false;
            }
            counter++;
        }
        list.push_back(obj);
        return true;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}


/*
    {Vor} : {isSorted(list) && dataOf(list.nodeAt(i)) == dataOf(list.nodeAt(j)) => i == j}
    P = {data != obj && list.next() !=  && list.at(counter) < obj}
    {Nach} : {isSorted(list) == true && list'.size() <= list.size()+1 && dataOf(list.nodeAt(i)) == dataOf(list.nodeAt(j)) => i == j, obj elementOf(list)}
    list'.size() >= list.size(), for a in list : a in list'

 */
