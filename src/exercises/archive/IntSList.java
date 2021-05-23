package exercises.archive;

import aud.SList;

import java.util.function.Predicate;

// This class "wraps" an instance of `SList<Integer>` (attribute
// `li`): Calls to `toString()` and `push_front()` are "delegated" to
// the instance.
// The additional method `filter()` applies a predicate and constructs
// a new list with all items for which the predicate evaluates to `true`.
//
public class IntSList {
    // Store list of integers as an SList.
    SList<Integer> li;

    public IntSList() {
        li = new SList<>();
    }

    // You must provide a main() method!
    public static void main(String[] args) {
        IntSList isl = new IntSList();
        for (int i = 1; i <= 100; i++) {
            isl.push_front(i);
        }
        IntSList filteredSList = isl.filter(x -> x % 2 == 1);
        System.out.println("Whole list: " + isl);
        System.out.println("Just even ints: " + isl.filter(x -> x % 2 == 0).toString());
        System.out.println("Just uneven ints: " + isl.filter(x -> x % 2 == 1).toString());
        System.out.println("All ints >= 50: " + isl.filter(x -> x >= 50).toString());
        System.out.println("All ints <= 50: " + isl.filter(x -> x <= 50).toString());
    }

    // delegate to SList
    @Override
    public String toString() {
        return li.toString();
    }

    // delegate to SList
    public void push_front(int obj) {
        li.push_front(obj);
    }

    // Filter list by predicate `p` and return list of all items for
    // which predicate evaluates to `true`.
    // The order of items in the returned list may differ from the order
    // in `this` list (because we use always `push_front()`).
    //
    public IntSList filter(Predicate<Integer> p) {
        IntSList newList = new IntSList();
        SList<Integer>.Iterator iterator = this.li.iterator();
        Integer nextInt = iterator.next();
        while (iterator.hasNext()) {
            if (p.test(nextInt)) newList.push_front(nextInt);
            nextInt = iterator.next();
        }
        if (p.test(nextInt)) newList.push_front(nextInt);
        return newList;
    }
}