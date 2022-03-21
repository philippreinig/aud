package aud.test;

import aud.Queue;
import aud.QueueDL;
import aud.adt.AbstractQueue;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QueueTest {

    public static void main(final String[] args) {
        org.junit.runner.JUnitCore.main("aud.test.QueueTest");
    }

    static void testQueue(final AbstractQueue<Integer> queue) {
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
        assertTrue(queue.front().intValue() == 1);

        assertTrue(queue.dequeue() == 1);
        assertTrue(queue.isEmpty());

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertTrue(queue.dequeue() == 1);
        assertTrue(queue.dequeue() == 2);
        assertTrue(queue.dequeue() == 3);
        assertTrue(queue.isEmpty());

        for (int i = 0; i < 10; ++i)
            queue.enqueue(i);
        queue.enqueue(-1);
        for (int i = 0; i < 100; ++i)
            queue.enqueue(i);
        queue.enqueue(-1);

        int n;
        for (int i = 0; ((n = queue.dequeue()) != -1); ++i)
            assertTrue(n == i);

        for (int i = 0; i < 100; ++i)
            queue.enqueue(i);
        queue.enqueue(-1);

        for (int i = 0; ((n = queue.dequeue()) != -1); ++i)
            assertTrue(n == i);
        for (int i = 0; ((n = queue.dequeue()) != -1); ++i)
            assertTrue(n == i);

        assertTrue(queue.isEmpty());
    }

    @Test
    public void testQueue() {
        final AbstractQueue<Integer> queue = new Queue<Integer>();
        QueueTest.testQueue(queue);
    }

    @Test
    public void testQueueDL() {
        final AbstractQueue<Integer> queue = new QueueDL<Integer>();
        QueueTest.testQueue(queue);
    }

    @Test(expected = NoSuchElementException.class)
    public void testInvalid_front() {
        final Queue<Integer> queue = new Queue<Integer>();
        queue.front();
    }

    @Test(expected = NoSuchElementException.class)
    public void testInvalid_dequeue() {
        final Queue<Integer> queue = new Queue<Integer>();
        queue.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void testInvalid_frontDL() {
        final QueueDL<Integer> queue = new QueueDL<Integer>();
        queue.front();
    }

    @Test(expected = NoSuchElementException.class)
    public void testInvalid_dequeueDL() {
        final QueueDL<Integer> queue = new QueueDL<Integer>();
        queue.dequeue();
    }
}
