package algorithms2.wordnet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;


public class WordNet {
    private ArrayList<Node> nodes;
    private SET<String> words_set;

    private class Node {
        private int id;
        //     private String word;
        //       private String word2;

        private ArrayList<String> word;
        private ArrayList<Integer> hypernyms;
        public Node (int id1) {
            id = id1;
            //   word = noun;
            //      word2 = null;
            word = new ArrayList<>();
            hypernyms = new ArrayList<>();
        }
        public void add_noun (String noun) {
            word.add(noun);
        }
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();

        // nodes = new Bag<>();
        nodes = new ArrayList<>();
        words_set = new SET<String>();

        In synset_input = new In(synsets);


        while (synset_input.hasNextLine()) {
            String[] split = synset_input.readLine().split(",");

            Node new_node = new Node(Integer.parseInt(split[0]));
            String[] words = split[1].split(" ");

            for (String i : words) {
                new_node.add_noun(i);
                words_set.add(i);
            }

            nodes.add(new_node);
        }
        synset_input.close();

        In hypernym_input = new In(hypernyms);
        while (hypernym_input.hasNextLine()) {
            String[] split = hypernym_input.readLine().split(",");
            int id = Integer.parseInt(split[0]);

            if (split.length > 1) {
                for (int i = 1; i < split.length; i++) {
                    int hypernym_wanted = Integer.parseInt(split[i]);

                    if (nodes.size() < id) throw new IllegalArgumentException();
                    if (nodes.size() < hypernym_wanted) throw new IllegalArgumentException();
                    nodes.get(id).hypernyms.add(hypernym_wanted);
                }
            }
        }
        hypernym_input.close();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        Queue<String> queue = new Queue<String>();

        for (String i : words_set) {
            queue.enqueue(i);
        }

        return queue;
    }


    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();

        return words_set.contains(word);
        /*

        for (Node i : nodes) {
            for (String j : i.word) {
                if (j.equals(word)) return true;
            }
        }

         */

        //return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();

        int[] a_distance = new int[nodes.size()]; // values represent distance of v and w to that node (node is index)
        int[] b_distance = new int[nodes.size()];

        Queue<Integer> a_queue = new Queue<>(); // queues used to avoid recursion
        Queue<Integer> b_queue = new Queue<>();

        for (int i = 0; i < nodes.size(); i++) { // initializing with -1, -1 means there is no connection
            a_distance[i] = -1;
            b_distance[i] = -1;
        }
        for (int i = 0; i < nodes.size(); i++) {
            Node current = nodes.get(i);

            for (String j : current.word) {
                if (j.equals(nounA)) {
                    a_queue.enqueue(i);
                    a_distance[i] = 0;
                }
                if (j.equals(nounB)) {
                    b_queue.enqueue(i);
                    b_distance[i] = 0;
                }
            }
        }

        if (a_queue.size() == 0 || b_queue.size() == 0) throw new IllegalArgumentException();

        while (!a_queue.isEmpty()) {
            int current = a_queue.dequeue();
            for (int i : nodes.get(current).hypernyms) { // all the possible ways to go from the current node
                if (a_distance[i] == -1) { // if it hasn't been visited before
                    a_distance[i] = a_distance[current] + 1;
                    a_queue.enqueue(i);
                }
                if (a_distance[current] + 1 < a_distance[i]) { // if it has been visited, but this is a shorter path
                    a_distance[i] = a_distance[current] + 1;
                }
            }
        }

        while (!b_queue.isEmpty()) { // doing the same with w; finding the distances of the nodes to w
            int current = b_queue.dequeue();
            for (int i : nodes.get(current).hypernyms) {
                if (b_distance[i] == -1) {
                    b_distance[i] = b_distance[current] + 1;
                    b_queue.enqueue(i);
                }
                if (b_distance[current] + 1 < b_distance[i]) {
                    b_distance[i] = b_distance[current] + 1;
                }
            }
        }

        int SAP_distance = 1000000; // the distance from v to w. initialize with something very high
        int ans = -1; // the node used to measure distance
        for (int i = 0; i < a_distance.length; i++) {
            if (a_distance[i] > -1 && b_distance[i] > -1 && a_distance[i] + b_distance[i] < SAP_distance) { // if node is connected to v and w
                SAP_distance = a_distance[i] + b_distance[i]; // update distance and sap
                ans = i;
            }
        }
        if (SAP_distance == 1000000) {
            SAP_distance = -1;
            ans = -1;
        }
        return SAP_distance;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();

        int[] a_distance = new int[nodes.size()]; // values represent distance of v and w to that node (node is index)
        int[] b_distance = new int[nodes.size()];

        Queue<Integer> a_queue = new Queue<>(); // queues used to avoid recursion
        Queue<Integer> b_queue = new Queue<>();

        for (int i = 0; i < nodes.size(); i++) { // initializing with -1, -1 means there is no connection
            a_distance[i] = -1;
            b_distance[i] = -1;
        }
        for (int i = 0; i < nodes.size(); i++) {
            Node current = nodes.get(i);

            for (String j : current.word) {
                if (j.equals(nounA)) {
                    a_queue.enqueue(i);
                    a_distance[i] = 0;
                }
                if (j.equals(nounB)) {
                    b_queue.enqueue(i);
                    b_distance[i] = 0;
                }
            }
        }

        if (a_queue.size() == 0 || b_queue.size() == 0) throw new IllegalArgumentException();

        while (!a_queue.isEmpty()) {
            int current = a_queue.dequeue();
            for (int i : nodes.get(current).hypernyms) { // all the possible ways to go from the current node
                if (a_distance[i] == -1) { // if it hasn't been visited before
                    a_distance[i] = a_distance[current] + 1;
                    a_queue.enqueue(i);
                }
                if (a_distance[current] + 1 < a_distance[i]) { // if it has been visited, but this is a shorter path
                    a_distance[i] = a_distance[current] + 1;
                }
            }
        }

        while (!b_queue.isEmpty()) { // doing the same with w; finding the distances of the nodes to w
            int current = b_queue.dequeue();
            for (int i : nodes.get(current).hypernyms) {
                if (b_distance[i] == -1) {
                    b_distance[i] = b_distance[current] + 1;
                    b_queue.enqueue(i);
                }
                if (b_distance[current] + 1 < b_distance[i]) {
                    b_distance[i] = b_distance[current] + 1;
                }
            }
        }

        int SAP_distance = 1000000; // the distance from v to w. initialize with something very high
        Node ans = nodes.get(0); // the node used to measure distance
        for (int i = 0; i < a_distance.length; i++) {
            if (a_distance[i] > -1 && b_distance[i] > -1 && a_distance[i] + b_distance[i] < SAP_distance) { // if node is connected to v and w
                SAP_distance = a_distance[i] + b_distance[i]; // update distance and sap
                ans = nodes.get(i);
            }
        }
        String ans_str = "";
        for (int i = 0; i < ans.word.size(); i++) {
            ans_str += ans.word.get(i);
            if (i < ans.word.size() - 1) {
                ans_str += " ";
            }
        }
        return ans_str;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet a = new WordNet("synsets3.txt", "hypernyms3.txt");
        System.out.println(a.sap("a","d"));
        System.out.println(a.distance("a","d"));
    }
}