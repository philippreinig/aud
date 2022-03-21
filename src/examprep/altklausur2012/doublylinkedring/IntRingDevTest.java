package examprep.altklausur2012.doublylinkedring;

import aud.util.DotViewer;

public class IntRingDevTest {
    public static RNode<Integer> find(final IntRing ring, final int x) {
        RNode<Integer> curr = ring.getHead();
        do {
            if (curr.getData() == x) return curr;
            curr = curr.getNext();
        } while (curr != ring.getHead());
        return null;
    }


    private static void printEachNodeOfRing(final Ring<?> ring) {
        for (final RNode<?> node : ring) {
            System.out.println(node);
        }
        System.out.println("-------------------------");
    }

    public static void main(final String[] args) {
        final IntRing intRing = new IntRing();


        intRing.insertFront(3);
        intRing.insertFront(2);
        intRing.insertFront(1);

        final RNode<Integer> ref = intRing.find(3);
//        for (int i = 1; i < 10; ++i) {
//                intRing.insertBack(i);
//        }

        final DotViewer dv = DotViewer.displayWindow(intRing.toDot(), "Doubly linked ring");
        intRing.insertBefore(ref, -1);
        printEachNodeOfRing(intRing);
        dv.display(intRing.toDot());


        for (int i = -1; i < 15; ++i) {
            System.out.println(i + ": find -> " + find(intRing, i));
        }


    }
}
