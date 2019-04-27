import edu.princeton.cs.algs4.StdOut;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 12/24/2016
 *  Description: Test class for deque.
 **************************************************************************** */
public class DequeTest {
    private Deque deque = new Deque();
    private Deque emptyDeque = new Deque();

    @Before
    public void setUp() {
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);

    }

    @Test
    public void isEmpty() {
        assert (deque.isEmpty() == false);
        assert (emptyDeque.isEmpty() == true);

    }

    @Test
    public void size() {
        assert (deque.size() == 6);
        assert (emptyDeque.size() == 0);

    }

    @Test
    public void addFirst() {
        deque.addFirst(7);
        assert (deque.size() == 7);
        deque.addFirst(8);
        deque.addLast(9);
        assert (deque.size() == 9);

    }

    @Test
    public void addLast() {
        deque.addLast(7);
        deque.addLast(8);
        deque.addLast(9);
        assert (deque.size() == 9);

    }

    @Test
    public void removeFirst() {
        assert (deque.removeFirst() == Integer.valueOf(3));
        assert (deque.removeFirst() == Integer.valueOf(2));
        assert (deque.removeFirst() == Integer.valueOf(1));
        assert (deque.removeFirst() == Integer.valueOf(4));
        assert (deque.removeFirst() == Integer.valueOf(5));
        assert (deque.removeFirst() == Integer.valueOf(6));

        // test nonempty-empty-nonempty
        deque.addFirst(1);
        assert (deque.removeFirst() == Integer.valueOf(1));
    }

    @Test
    public void testAddFirstAddLast() {
        emptyDeque.addFirst(1);
        emptyDeque.addLast(2);
        emptyDeque.addLast(3);
        emptyDeque.addLast(4);

        Iterator iter = emptyDeque.iterator();

        while (iter.hasNext()) {
            StdOut.println(iter.next());
        }

        assert (emptyDeque.removeFirst() == Integer.valueOf(1));
        assert (emptyDeque.removeFirst() == Integer.valueOf(2));
        assert (emptyDeque.removeFirst() == Integer.valueOf(3));
        assert (emptyDeque.removeFirst() == Integer.valueOf(4));
    }

    @Test
    public void testAddFirstRemoveLast() {
        emptyDeque.addFirst(1);
        assert (emptyDeque.removeLast() == Integer.valueOf(1));
    }

    @Test
    public void testAddLastRemoveFirst() {
        emptyDeque.addLast(1);
        assert (emptyDeque.removeFirst() == Integer.valueOf(1));
    }

    @Test
    public void removeLast() {
        assert (deque.removeLast() == Integer.valueOf(6));
        assert (deque.removeLast() == Integer.valueOf(5));
        assert (deque.removeLast() == Integer.valueOf(4));
        assert (deque.removeLast() == Integer.valueOf(1));
        assert (deque.removeLast() == Integer.valueOf(2));
        assert (deque.removeLast() == Integer.valueOf(3));

        // test nonempty-empty-nonempty
        deque.addLast(1);
        assert (deque.removeLast() == Integer.valueOf(1));
    }

    @Test
    public void iterator() {
        Iterator iterFirst = deque.iterator();
        Iterator iterSecond = deque.iterator();
        while (iterFirst.hasNext() &&
                iterSecond.hasNext()) {
            StdOut.printf("current element from first iter is %d\n",
                          iterFirst.next());
            StdOut.printf("current element from second iter is %d\n",
                          iterSecond.next());
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFirstIllegalArgument() {
        deque.addFirst(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddLastIllegalArgument() {
        deque.addLast(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFirstNoSuchElement() {
        emptyDeque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveLastNoSuchElement() {
        emptyDeque.removeLast();
    }


}
