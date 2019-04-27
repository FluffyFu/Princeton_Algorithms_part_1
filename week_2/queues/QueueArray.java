/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 2018/12/16
 *  Description: Implement queue with resizing-array.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueArray<Item> implements Iterable<Item> {

    private int front, rear, size;
    private Item[] array;

    public QueueArray() {
        array = (Item[]) new Object[2];
        front = 0;
        size = 0;
        rear = array.length - 1;
    }

    boolean isEmpty() {
        return size == 0;
    }

    boolean isFull() {
        return size == array.length;
    }

    void enqueue(Item item) {
        // double array size if it is full
        if (isFull()) {
            doubleArray();
        }
        rear = (rear + 1) % array.length;
        array[rear] = item;
        size++;

    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        else {
            Item item = array[front];
            // prevent loitering (item also points to the int. However item is
            // a local variable, and the ref will be removed once the function call
            // is done.)
            array[front] = null;
            front = (front + 1) % array.length;
            size--;

            // shrink array size by half if the array is quarterly full
            // also make sure the array can be halved.
            if ((size == array.length / 4)
                    && (size > 0)) {
                halfArray();
            }
            return item;
        }
    }

    // double the array size and copy the elements to the new array
    private void doubleArray() {
        Item[] newArray = (Item[]) new Object[array.length * 2];

        // the queue order matches the array order (front = 0, rear = array.length - 1)
        if (front <= rear) {
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }
        }
        else {
            // first part
            int numFirst = size - front;
            for (int i = 0; i < numFirst; i++) {
                newArray[i] = array[front + i];
            }
            // second part
            for (int i = numFirst; i < size; i++) {
                newArray[i] = array[i - numFirst];
            }
        }
        array = newArray;
        // update the front and end in the new array.
        front = 0;
        rear = array.length / 2 - 1;
    }

    // half the array size and copyt the elements to the new array
    private void halfArray() {
        Item[] newArray = (Item[]) new Object[array.length / 2];

        // the elements are together
        if (front < rear) {
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i + front];
            }
        }
        // the elements spread into two parts
        else {
            // first part
            int numFirst = size - front;
            for (int i = 0; i < numFirst; i++) {
                newArray[i] = array[i + front];
            }
            // second part
            for (int i = numFirst; i < size; i++) {
                newArray[i] = array[i - numFirst];
            }
        }
        array = newArray;
        // update the front and end in the new array
        front = 0;
        rear = array.length / 2 - 1;
    }

    public Item front() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        else {
            return array[front];
        }
    }

    public Item rear() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        else {
            return array[rear];
        }
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int counter;
        private int currentIndex;

        public ArrayIterator() {
            counter = size;
            currentIndex = front;
        }

        public boolean hasNext() {
            return counter > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item element = array[currentIndex];
            currentIndex = (currentIndex + 1) % array.length;
            return element;
        }


    }

    public static void main(String[] args) {

        QueueArray queue = new QueueArray();
        int n = 20;

        for (int i = 0; i < n; i++) {
            queue.enqueue(i);
            StdOut.println("array size: " + queue.array.length);
            StdOut.println("queue size: " + queue.size);
        }

        int i = n - 1;
        while (i >= 0) {
            StdOut.println("array size: " + queue.array.length);
            StdOut.println("queue size: " + queue.size);
            StdOut.println(queue.dequeue());
            i--;
        }
        // StdOut.println("array size: " + queue.array.length);
        // StdOut.println("queue size: " + queue.size);
        // StdOut.println(queue.dequeue());
        // StdOut.println("array size: " + queue.array.length);
        // StdOut.println("queue size: " + queue.size);
        // StdOut.println(queue.dequeue());
        // StdOut.println("array size: " + queue.array.length);
        // StdOut.println("queue size: " + queue.size);
        // StdOut.println(queue.dequeue());
        // StdOut.println(queue.dequeue());
        // StdOut.println(queue.front());
        // StdOut.println(queue.rear());

    }
}
