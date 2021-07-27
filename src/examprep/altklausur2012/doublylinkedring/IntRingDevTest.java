package examprep.altklausur2012.doublylinkedring;

import aud.util.DotViewer;

public class IntRingDevTest {

    private static void printEachNodeOfRing(final Ring<?> ring) {
        for (final Ring.RNode<?> node : ring) {
            System.out.println(node);
        }
        System.out.println("-------------------------");
    }

    public static void main(final String[] args) {
        final IntRing intRing = new IntRing();

        Ring.RNode<Integer> ref = null;

        for (int i = 1; i < 10; ++i) {
            if (i == 5) {
                ref = intRing.insertBack(i);
            } else {
                intRing.insertBack(i);
            }
//            System.out.println(intRing);
        }

        final DotViewer dv = DotViewer.displayWindow(intRing.toDot(), null);
        intRing.insertBefore(ref, -1);
        dv.display(intRing.toDot());


        for (int i = 0; i < 15; ++i) {
            System.out.println(i + ": find -> " + intRing.find(i));
        }


    }
}
