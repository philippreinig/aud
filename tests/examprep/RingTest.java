package examprep;

import org.junit.Test;

public class RingTest {

    @Test
    public void remove() {
    }

    @Test
    public void testRemove() {
    }

    @Test
    public void insertFront() {
        final int TEST_AMOUNT = 10;
        final Ring<Integer> ring = new Ring<>();
        for (int i = 0; i < TEST_AMOUNT; ++i) {
            ring.insertFront(i);
        }
        for (int i = 0; i < TEST_AMOUNT; ++i) {
            System.out.println("i=" + i + ": expected element: " + (TEST_AMOUNT - i - 1) + ", found: " + ring.get(i).data);
            assert (ring.get(i).data == TEST_AMOUNT - i - 1);
        }
    }

    @Test
    public void insertBack() {
    }

    @Test
    public void insertAfter() {
    }

    @Test
    public void insertBefore() {
    }

    @Test
    public void find() {
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void iterator() {
    }

    @Test
    public void toDot() {
    }

    @Test
    public void show() {
    }

    @Test
    public void testToString() {
    }
}