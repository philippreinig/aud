package examprep;

public class RingTest {

    private static void printEachNodeOfRing(final Ring<?> ring) {
        for (final Ring.RNode<?> node : ring) {
            System.out.println(node);
        }
        System.out.println("-------------------------");
    }

    public static void main(final String[] args) {
        final IntRing intRing = new IntRing();

        for (int i = 0; i < 10; ++i) {
            intRing.insertFront(i);
            System.out.println(intRing);
        }


    }
}
