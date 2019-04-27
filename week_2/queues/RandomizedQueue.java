/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 12/24/2018
 *  Description: A randomized queue is similar to a stack or queue, except that
 *      the item removed is chosen uniformly at random from items in the data
 *      structure.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size, rear;
    private Item[] array;

    public RandomizedQueue() {
        size = 0;
        rear = 0;
        array = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == array.length) {
            resizeArray(array.length * 2);
        }
        array[rear++] = item;
        size++;
    }

    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException();

        int sampleIndex = StdRandom.uniform(size);

        // move the rear element to the sampled element location.
        Item sampledElement = array[sampleIndex];
        array[sampleIndex] = array[--rear];
        array[rear] = null;
        size--;


        if (size == array.length / 4 &&
                size > 0) {
            resizeArray(array.length / 2);
        }
        return sampledElement;
    }

    public Item sample() {
        if (size == 0) throw new NoSuchElementException();

        int sampleIndex = StdRandom.uniform(size);
        return array[sampleIndex];
    }

    public Iterator<Item> iterator() {
        return new randomizedArrayIterator();
    }

    private class randomizedArrayIterator implements Iterator<Item> {
        private int iterIndex;
        private Item[] randomizedArray;

        public randomizedArrayIterator() {
            iterIndex = size - 1;
            randomizedArray = shuffle(array, rear);
        }

        public boolean hasNext() {
            return iterIndex >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item currentElement = randomizedArray[iterIndex];
            iterIndex--;
            return currentElement;
        }

    }

    private Item[] shuffle(Item[] array, int size) {
        Item[] randomizedArray = (Item[]) new Object[size];

        for (int i = 0; i < size; i++) {
            randomizedArray[i] = array[i];
        }

        for (int i = 0; i < size; i++) {
            int r = StdRandom.uniform(i + 1);
            exchange(randomizedArray, i, r);
        }
        return randomizedArray;
    }

    private void exchange(Item[] array, int i, int j) {
        Item temp = array[i];
        array[i] = array[j];
        array[j] = temp;

    }

    private void resizeArray(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < rear; i++) {
            copy[i] = array[i];
        }
        array = copy;

    }

    public static void main(String[] args) {
    }
}
