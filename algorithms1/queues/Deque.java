package algorithms1.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item>  implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty(){
        return (size == 0);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == 0) {
            first = new Node();
            first.item = item;
            first.next = null;
            last = first;

        } else {
            Node old_first = first;
            first = new Node();
            first.item = item;
            first.next = old_first;
        }

        size ++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == 0) {
            last = new Node();
            last.item = item;
            last.next = null;
            first = last;
        } else {
            Node old_last = last;
            last = new Node();
            last.item = item;
            last.next = null;
            old_last.next = last;
        }

        size ++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        size --;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        if (size == 1) {
            Item item = last.item;
            first = null;
            last = null;
            size --;
            return item;
        }
        else {
            Item item = last.item;
            Node second_last = first;
            while (second_last.next.next != null) {
                second_last = second_last.next;
            }
            last = second_last;
            second_last.next = null;
            size --;
            return item;
        }
    }

    public Iterator<Item> iterator() {
        return new ListIterator();

    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        private int index = 0;

        public ListIterator(){
        }


        public boolean hasNext() {
            return (index < size);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            index ++;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

    }

}
