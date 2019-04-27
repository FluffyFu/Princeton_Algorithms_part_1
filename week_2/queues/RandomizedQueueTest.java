import edu.princeton.cs.algs4.StdOut;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 12/25/2018
 *  Description: Tests for RandomizedQueue.
 **************************************************************************** */
public class RandomizedQueueTest {
    private RandomizedQueue randQueue = new RandomizedQueue();
    private RandomizedQueue emptyRandQueue = new RandomizedQueue();

    @Before
    public void setUp() {
        randQueue.enqueue(1);
        randQueue.enqueue(2);
        randQueue.enqueue(3);
        randQueue.enqueue(4);
        randQueue.enqueue(5);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void isEmpty() {
        assert (randQueue.isEmpty() == false);
        assert (emptyRandQueue.isEmpty() == true);
    }

    @Test
    public void size() {
        assert (randQueue.size() == 5);
        assert (emptyRandQueue.size() == 0);
    }

    @Test
    public void enqueue() {
        randQueue.enqueue(6);
        assert (randQueue.size() == 6);

    }

    @Test
    public void dequeue() {
        for (int i = randQueue.size(); i > 0; i--) {
            StdOut.printf("current element: %s\n",
                          randQueue.dequeue().toString());
            assert (randQueue.size() == i - 1);
        }

        // make sure the data structure behaves correctly when it
        // goes from non-empty to empty and then back to non-empty
        randQueue.enqueue(1);
        randQueue.enqueue(2);
        assert (randQueue.size() == 2);

    }

    @Test
    public void sample() {
        randQueue.sample();
        assert (randQueue.size() == 5);
    }

    /*
    Test multiple iterators can be used simultaneously and independently.
     */
    @Test
    public void iterator() {
        Iterator iterFirst = randQueue.iterator();
        Iterator iterSecond = randQueue.iterator();
        while (iterFirst.hasNext() &&
                iterSecond.hasNext()) {
            StdOut.printf("from first iter: %s\n", iterFirst.next().toString());
            StdOut.printf("from second iter: %s\n", iterSecond.next().toString());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEqueueNullArgument() {
        randQueue.enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDequeueEmptyArray() {
        emptyRandQueue.dequeue();
        emptyRandQueue.sample();
    }

    @Test(expected = NoSuchElementException.class)
    public void testEmptyIterator() {
        Iterator iter = emptyRandQueue.iterator();
        iter.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemoveMethod() {
        Iterator iter = emptyRandQueue.iterator();
        iter.remove();
    }
}
