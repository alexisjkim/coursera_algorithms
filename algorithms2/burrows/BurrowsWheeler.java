import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String string = BinaryStdIn.readString();

        String[] sorted = new String[string.length()];

        for (int i = 0; i < string.length(); i++) {
            StringBuilder st = new StringBuilder();
            for (int j = i; j < string.length(); j++) {
                st.append(string.charAt(j));
            }
            for (int j = 0; j < i; j++) {
                st.append(string.charAt(j));
            }
            sorted[i] = st.toString();
        }
        Arrays.sort(sorted);

        StringBuilder st = new StringBuilder();
        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i].equals(string)) BinaryStdOut.write(i);
            st.append(sorted[i].charAt(string.length() - 1));

        }
        BinaryStdOut.write(st.toString());
        BinaryStdIn.close();
        BinaryStdOut.close();





    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {

    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) BurrowsWheeler.transform();
        if (args[0].equals("+")) BurrowsWheeler.inverseTransform();

    }

}