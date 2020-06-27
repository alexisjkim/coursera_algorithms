/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package algorithms2.boggle;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.TrieSET;

import java.util.ArrayList;

public class BoggleSolver {
    private TrieSET set;
    private char[][] graph; // the board represented in an int[][]
    private Node head; // head
    private Stack<String> words; // all words in dictionary

    private static class Node { // node for trie
        private Object value;
        private boolean last = false; //is it last letter of a word?
        private Node[] next = new Node[26];

    }

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException();
        head = new Node();

        for (String word : dictionary) {
            if (word == null) throw new IllegalArgumentException();
            int d = 0;
            Node x = head; // current node

            while (d < word.length()) {
                char c = word.charAt(d);
                int index = (int) c - 65;

                if (x.next[index] == null) {
                    Node new_node = new Node();
                    new_node.value = c;
                    x.next[index] = new_node;
                    x = new_node;
                }
                else {
                    x = x.next[index];
                }
                d++;
            }
            x.last = true;
        }
    }

    private int toIndex(int i, int j) { // converts i, j to index number
        if (i < 0 || i > graph.length) return -1;
        if (j < 0 || j > graph.length) return -1;
        return ((i * graph.length) + j);
    }


    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                graph[i][j] = board.getLetter(i,j);
            }
        }

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                findWords(head, i, j, Character.toString(graph[i][j]), new ArrayList<Integer>());
            }
        }
        return words;
    }

    private void findWords(Node node, int i, int j, String string, ArrayList<Integer> past) {
        if (node == null) return;
        if (inTrie(string) == 1) words.push(string);

        if (!past.contains(toIndex(i-1,j)) && inTrie(string) == 0 && toIndex(i-1,j) != -1) { // if this dice is unvisited, the prefix is in the string, and the next looked at die is valid
            past.add(toIndex(i-1,j));
            findWords(node.next[(int) graph[i-1][j] - 65], i-1, j, string + graph[i][j], past);
        }
        if (!past.contains(toIndex(i-1,j-1)) && inTrie(string) == 0 && toIndex(i-1,j-1) != -1) {
            past.add(toIndex(i-1,j-1));
            findWords(node.next[(int) graph[i-1][j-1] - 65], i-1, j-1, string + graph[i][j], past);
        }
        if (!past.contains(toIndex(i,j-1)) && inTrie(string) == 0 && toIndex(i,j-1) != -1) {
            past.add(toIndex(i,j-1));
            findWords(node.next[(int) graph[i][j-1] - 65], i, j-1, string + graph[i][j], past);
        }
        if (!past.contains(toIndex(i+1,j-1)) && inTrie(string) == 0 && toIndex(i+1,j-1) != -1) {
            past.add(toIndex(i+1,j-1));
            findWords(node.next[(int) graph[i+1][j-1] - 65], i+1, j-1, string + graph[i][j], past);
        }
        if (!past.contains(toIndex(i+1,j)) && inTrie(string) == 0 && toIndex(i+1,j) != -1) {
            past.add(toIndex(i+1,j));
            findWords(node.next[(int) graph[i+1][j] - 65], i+1, j, string + graph[i][j], past);
        }
        if (!past.contains(toIndex(i+1,j+1)) && inTrie(string) == 0 && toIndex(i+1,j+1) != -1) {
            past.add(toIndex(i+1,j+1));
            findWords(node.next[(int) graph[i+1][j+1] - 65], i+1, j+1, string + graph[i][j], past);
        }
        if (!past.contains(toIndex(i,j+1)) && inTrie(string) == 0 && toIndex(i,j+1) != -1) {
            past.add(toIndex(i,j+1));
            findWords(node.next[(int) graph[i][j+1] - 65], i, j+1, string + graph[i][j], past);
        }
        if (!past.contains(toIndex(i-1,j+1)) && inTrie(string) == 0 && toIndex(i-1,j+1) != -1) {
            past.add(toIndex(i-1,j+1));
            findWords(node.next[(int) graph[i-1][j+1] - 65], i-1, j+1, string + graph[i][j], past);
        }
    }

    private int inTrie(String string) { // 0 = no path in tree not even as prefix, 1 = there as prefix, 2 = there as word]
        int d = 0;
        Node node = head;
        while (d < string.length()) {
            char c = string.charAt(d);
            if (node.next[(int) c - 65] == null) return -1;
            else {
                node = node.next[(int) c - 65];
            }
            d++;
        }
        if (node.last) return 1;
        else return 0;

    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null) throw new IllegalArgumentException();
        int length = word.length();

        for (int i = 0; i < length - 1; i++) {
            if (word.charAt(i) == 'Q' && word.charAt(i+1) == 'U') length--;
        }
        if (length == 3 || length == 4) {
            return 1;
        }
        if (length == 5) {
            return 2;
        }
        if (length == 6) {
            return 3;
        }
        if (length == 7) {
            return 5;
        }
        if (length > 7) {
            return 11;
        }
        return 0;
    }

    public static void main(String[] args) {
        /*
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);

         */

        String[] words = new String[2];
        words[0] = "ABC";
        words[1] = "ACB";
        BoggleSolver a = new BoggleSolver(words);

    }
}
