package algorithms1.queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first;
    private int size = 0;

    private class Node {
        private Item item;
        private Node next;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        Node old_first = first;
        first = new Node();
        first.item = item;
        first.next = old_first;
        size ++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("empty queue");
        } if (size == 1) {
            Node pointer = first;
            first = null;
            size --;
            return pointer.item;
        } else {
            int index = StdRandom.uniform(0, size);

            if (index == 0) {
                Node remove = first;
                first = first.next;
                size --;
                return remove.item;
            } else {
                Node pointer = first;

                for (int i = 0; i < index-1; i++) {
                    pointer = pointer.next;
                }
                Node remove = pointer.next;
                pointer.next = pointer.next.next;
                size--;
                return remove.item;
            }
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("empty queue");
        }
        int index = StdRandom.uniform(0,size);
        Node pointer = first;

        for (int i = 0; i < index; i++){
            pointer = pointer.next;
        }
        return pointer.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();

    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        private Item[] order;
        private int index;

        public ListIterator(){
            order = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                order[i] = current.item;
                current = current.next;
            }
            StdRandom.shuffle(order);
            index = 0;

        }


        public boolean hasNext() {
            return index < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = order[index];
            index ++;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        System.out.println(rq.size());
        rq.enqueue(178);
        rq.dequeue();
        rq.enqueue(230);
        System.out.println(rq.isEmpty());
        rq.enqueue(473);
        System.out.println(rq.isEmpty());
        rq.enqueue(39);
        System.out.println(rq.dequeue());

    }

}
