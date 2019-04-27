/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 12/24/2018
 *  Description: Algorithm Class Week 2 Assignment. Use a resizing array to
 *      implement a deque data structure.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size, front, rear;
    private Item[] array;

    public Deque() {
        array = (Item[]) new Object[2];
        front = 0;  // maintain a pointer for the array front.
        rear = 1;   // maintain a pointer for the array end.
        size = 0;   // number of elements in the deque.
    }

    /*
    Return is the deque is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /*
    Return the current deque size.
     */
    public int size() {
        return size;
    }

    /*
    Add an element to the deque from the front.
     */
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == array.length) {
            resizeArray(array.length * 2);
        }
        array[front] = item;
        front = modulo(front - 1, array.length);

        size++;
    }

    /*
    Add an element to the deque from the end.
     */
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == array.length) {
            resizeArray(array.length * 2);
        }
        array[rear] = item;
        rear = modulo(rear + 1, array.length);

        size++;
    }

    /*
    Remove one element from deque front.
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        front = modulo(front + 1, array.length);
        Item item = array[front];

        // avoid loitering
        array[front] = null;
        size--;

        if (size == array.length / 4 &&
                size > 0) {
            resizeArray(array.length / 2);
        }
        return item;
    }

    /*
    Remove one element from deque end.
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        rear = modulo(rear - 1, array.length);
        Item item = array[rear];
        // avoid loitering
        array[rear] = null;
        size--;

        if (size == array.length / 4 &&
                size > 0) {
            resizeArray(array.length / 2);
        }
        return item;
    }


    public Iterator<Item> iterator() {
        return new DequeArrayIterator();
    }

    private class DequeArrayIterator implements Iterator<Item> {
        private int counter;
        private int front_index;

        public DequeArrayIterator() {
            counter = size - 1;
            front_index = modulo(front + 1, array.length);
        }

        public boolean hasNext() {
            return counter >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = array[front_index];
            front_index = modulo(front_index + 1, array.length);
            counter--;
            return item;
        }
    }

    /*
    Resize the array to capacity and keep the deque order.
     */
    private void resizeArray(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            int frontIndex = modulo(front + i + 1, array.length);
            newArray[i] = array[frontIndex];
        }
        array = newArray;
        front = capacity - 1;
        rear = size;
    }

    private int modulo(int num, int base) {
        int result = num % base;
        if (result < 0) result += base;
        return result;
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        for (int i = 0; i < 20; i++) {
            deque.addFirst(i);
        }

        for (int item : deque) {
            StdOut.println(item);
        }
    }
}
