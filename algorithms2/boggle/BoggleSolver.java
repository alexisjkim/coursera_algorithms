/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package algorithms2.boggle;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.TrieSET;

public class BoggleSolver {
    private TrieSET set;
    private int[][] graph; // the board represented in an int[][]
    private Node head; // head

    private static class Node { // node for trie
        private Object value;
        private Node[] next = new Node[26];

    }

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
      //  for (String i : dictionary) set.add(i); // creating set with all words in dictionary
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

        }

    }



    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        Stack<String> stack = new Stack<>();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                graph[i][j] = board.getLetter(i,j);
            }
        }

        return new Stack<>();
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
