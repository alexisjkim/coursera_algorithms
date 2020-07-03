package algorithms2.burrows;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;

import java.util.HashMap;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {

        String string = BinaryStdIn.readString();

        int first = 0;

        StringBuilder st = new StringBuilder();

        CircularSuffixArray a = new CircularSuffixArray(string);

        for (int i = 0; i < string.length(); i++) {
            int index = a.index(i);

            if (index == 0) {
                first = i;
                index = string.length();
            }
            char c = string.charAt(index - 1);
            st.append(c);
        }

        BinaryStdOut.write(first);
        BinaryStdOut.write(st.toString());
        BinaryStdIn.close();
        BinaryStdOut.close();





    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {

        int first = BinaryStdIn.readInt();
        String string = BinaryStdIn.readString(); // last char in sorted suffixes (t[]

        int[] next = new int[string.length()];
        char[] aux = new char[string.length()];
        HashMap<Character, Queue<Integer>> map = new HashMap<>();

        int N = string.length();
        int[] count = new int[257];
        for (int i = 0; i < N; i++)
            count[string.charAt(i)+1]++;
        for (int r = 0; r < 256; r++)
            count[r+1] += count[r];
        for (int i = 0; i < N; i++) {
            if (!map.containsKey(string.charAt(i))){
                Queue<Integer> queue = new Queue<>();
                queue.enqueue(i);
                map.put(string.charAt(i), queue);
            }
            else {
                Queue<Integer> queue = map.get(string.charAt(i));
                queue.enqueue(i);
                map.put(string.charAt(i), queue);
            }

            aux[count[string.charAt(i)]++] = string.charAt(i);
        }

        for (int i = 0; i < N; i++) {
            next[i] = map.get(aux[i]).dequeue();
        }


        StringBuilder st = new StringBuilder();
        int next_index = first;
        for (int i = 0; i < string.length(); i++) {
            st.append(aux[next_index]);
            next_index = next[next_index];
        }

        BinaryStdOut.write(st.toString());
        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) BurrowsWheeler.transform();
        if (args[0].equals("+")) BurrowsWheeler.inverseTransform();

    }

}