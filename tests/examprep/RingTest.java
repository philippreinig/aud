package examprep;

import examprep.altklausur2012.doublylinkedring.IntRing;
import examprep.altklausur2012.doublylinkedring.RNode;
import examprep.altklausur2012.doublylinkedring.Ring;
import org.junit.Test;

import java.util.Iterator;

public class RingTest {

    @Test
    public void insertFront() {
        final int TEST_AMOUNT = 10;
        final IntRing ring = new IntRing();
        for (int i = 0; i < TEST_AMOUNT; ++i) {
            ring.insertFront(i);
        }
        for (int i = 0; i < TEST_AMOUNT; ++i) {
            final int dataReturned = ring.getNthNode(i).getData();
            final int expected = TEST_AMOUNT - i - 1;
            assert (dataReturned == expected) : "expected: " + expected + ", but got: " + dataReturned;
        }
    }

    @Test
    public void insertBack() {
        final int TEST_AMOUNT = 10;
        final Ring<Integer> ring = new Ring<>();
        for (int i = 0; i < TEST_AMOUNT; ++i) {
            ring.insertBack(i);
        }
        for (int i = 0; i < TEST_AMOUNT; ++i) {
            final int data = ring.getNthNode(i).getData();
            assert (data == i) : "expected element: " + i + ", found: " + data;
        }
    }

    @Test
    public void getData_TEST() {
        final int TEST_AMOUNT = 10;
        final IntRing intRing = new IntRing();
        for (int i = 0; i < 10; ++i) {
            intRing.insertBack(i);
        }

        for (int i = 0; i < 10; ++i) {
            final int data = intRing.getNthNode(i).getData();
            assert (data == i) : "expected " + i + ", but got " + data;
        }

    }

    @Test
    public void isEmpty_TEST() {
        final String EXPECTED_NOT_EMPTY_BUT_IS = "expected iterator not to be empty, but is";
        final String EXPECTED_EMPTY_BUT_ISNT = "expected iterator to be empty, but isn't";

        final IntRing emptyIntRing = new IntRing();
        assert (emptyIntRing.isEmpty()) : EXPECTED_EMPTY_BUT_ISNT;

        final IntRing filledIntRing = new IntRing();
        filledIntRing.insertFront(1);
        assert (!filledIntRing.isEmpty()) : EXPECTED_NOT_EMPTY_BUT_IS;
        filledIntRing.insertFront(2);
        assert (!filledIntRing.isEmpty()) : EXPECTED_NOT_EMPTY_BUT_IS;
        filledIntRing.insertFront(3);
        assert (!filledIntRing.isEmpty()) : EXPECTED_NOT_EMPTY_BUT_IS;
    }

    @Test
    public void iterator_TEST() {
        final int TEST_AMOUNT = 10;
        final IntRing intRing = new IntRing();
        for (int i = 0; i < TEST_AMOUNT; ++i) {
            intRing.insertBack(i);
        }
        final Iterator<RNode<Integer>> iter = intRing.iterator();
        for (int i = 0; i < TEST_AMOUNT; ++i) {
            assert (iter.hasNext()) : "expected iterator to have next element, but doesn't";
            final int next = iter.next().getData();
            assert (next == i) : "expected next element to be " + i + ", but is: " + next;
        }
        assert (!iter.hasNext()) : "expected iterator not to have next element, but does";
    }

    @Test
    public void remove_TEST() {
    }

    @Test
    public void insertAfter_TEST() {

    }

    @Test
    public void insertBefore_TEST() {

    }

    @Test
    public void find_TEST() {

    }
}