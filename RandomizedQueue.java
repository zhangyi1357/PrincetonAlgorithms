/* *****************************************************************************
 *  Name:        Zhang Yi
 *  Date:        2022.03.02
 *  Description: implementation of RandomizedQueue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int DEFAULT_CAPACITY = 8;
    private Item[] s; // array of items
    private int tail; // tail points to next of the last element

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[DEFAULT_CAPACITY];
        tail = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return tail == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return tail;
    }

    // add the item
    public void enqueue(Item item) {
        validateNotNull(item);
        if (tail == s.length) resize(s.length * 2);
        s[tail++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        validateQueueNotEmpty();
        if (tail * 4 <= s.length) resize(s.length / 2);
        int index = StdRandom.uniform(tail);
        Item item = s[index];
        s[index] = s[--tail];
        s[tail] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        validateQueueNotEmpty();
        int index = StdRandom.uniform(tail);
        return s[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ReverseIterator();
    }

    private class ReverseIterator implements Iterator<Item> {
        private int[] permutation;
        private int remainingItemNum;

        public ReverseIterator() {
            remainingItemNum = tail;
            permutation = new int[tail];
            for (int i = 0; i < tail; ++i)
                permutation[i] = i;
        }

        public boolean hasNext() {
            return remainingItemNum > 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int index = StdRandom.uniform(remainingItemNum--);
            Item item = s[permutation[index]];
            permutation[index] = permutation[remainingItemNum];
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void resize(int newCapacity) {
        Item[] copy = (Item[]) new Object[newCapacity];
        System.arraycopy(s, 0, copy, 0, tail);
        s = copy;
    }

    private void validateNotNull(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    private void validateQueueNotEmpty() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        int testTimes = 100, maxNum = 100;

        for (int i = 0; i < testTimes; ++i)
            rq.enqueue(StdRandom.uniform(maxNum));
        StdOut.println("Now delete elements in the deque");
        for (int i = 0; i < testTimes / 2; ++i)
            StdOut.printf("%d ", rq.dequeue());
        StdOut.println("\nNow print elements remaining in the deque");
        for (int x : rq)
            StdOut.print(x + " ");
        StdOut.println("\nSize of the dq now is " + rq.size());
        StdOut.println("Now deleteing all of the elements in the deque");
        while (!rq.isEmpty())
            StdOut.printf("%d ", rq.dequeue());
    }
}
