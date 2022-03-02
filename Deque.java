/* *****************************************************************************
 *  Name:        Zhang Yi
 *  Date:        2022.03.02
 *  Description: implementation of Deque using array with dynamic resizing
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private static final int DEFAULT_CAPACITY = 8;
    private Item[] s;       // array of items
    // head and tail index of the deque
    // head points to the first element
    // tail points to next of the last element
    private int head, tail;
    private int sz;         // size of deque

    // construct an empty deque
    public Deque() {
        s = (Item[]) new Object[DEFAULT_CAPACITY];
        sz = 0;
        head = 0;
        tail = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return sz == 0;
    }

    // return the number of items on the deque
    public int size() {
        return sz;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateNotNull(item);
        if (sz == s.length) resize(s.length * 2);
        head = getIndex(head - 1);
        s[head] = item;
        ++sz;
    }

    // add the item to the back
    public void addLast(Item item) {
        validateNotNull(item);
        if (sz == s.length) resize(s.length * 2);
        s[tail] = item;
        tail = getIndex(tail + 1);
        ++sz;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        validateDequeNotEmpty();
        if (sz * 4 <= s.length) resize(s.length / 2);
        Item item = s[head];
        s[head] = null;
        head = getIndex(head + 1);
        sz--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        validateDequeNotEmpty();
        if (sz * 4 <= s.length) resize(s.length / 2);
        tail = getIndex(tail - 1);
        Item item = s[tail];
        s[tail] = null;
        sz--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeSequenceIterator();
    }

    private class DequeSequenceIterator implements Iterator<Item> {
        private int i;

        public DequeSequenceIterator() {
            i = head;
        }

        public boolean hasNext() {
            return i != tail;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = s[i];
            i = getIndex(i + 1);
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void validateNotNull(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    private void validateDequeNotEmpty() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
    }

    // get index of the element
    private int getIndex(int k) {
        int sLength = s.length;
        if (k < 0)
            return k + sLength;
        else if (k >= sLength)
            return k - sLength;
        else
            return k;
    }

    private void resize(int newCapacity) {
        Item[] copy = (Item[]) new Object[newCapacity];
        if (head >= tail) {
            System.arraycopy(s, head, copy, 0, s.length - head);
            System.arraycopy(s, 0, copy, s.length - head, tail);
        }
        else {
            System.arraycopy(s, head, copy, 0, tail - head);
        }
        s = copy;
        head = 0;
        tail = sz;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
        int testTimes = 10, maxNum = 100;
        for (int i = 0; i < testTimes; ++i) {
            dq.addFirst(StdRandom.uniform(maxNum));
            dq.addLast(StdRandom.uniform(maxNum));
        }
        StdOut.println("Now delete elements in the deque");
        for (int i = 0; i < testTimes / 2; ++i) {
            StdOut.printf("%d %d ", dq.removeFirst(), dq.removeLast());
        }
        StdOut.println("\nNow print elements remaining in the deque");
        for (int x : dq) {
            StdOut.print(x + " ");
        }
        StdOut.println("\nSize of the dq now is " + dq.size());
        StdOut.println("Now deleteing all of the elements in the deque");
        while (!dq.isEmpty()) {
            StdOut.printf("%d ", dq.removeFirst());
        }

    }


}
