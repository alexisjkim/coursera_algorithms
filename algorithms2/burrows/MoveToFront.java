package algorithms2.burrows;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] alphabet = new char[256];
        for (int i = 0; i < 256; i++) { // initializing alphabet values
            alphabet[i] = (char) i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            char buffer = alphabet[0];

            for (int i = 1; i < 256; i++) {
                char temp_buffer = alphabet[i];
                alphabet[i] = buffer;
                buffer = temp_buffer;

                if (alphabet[i] == c) {
                    BinaryStdOut.write(i);
                    alphabet[0] = c;
                    break;
                }
            }
        }

        BinaryStdIn.close();
        BinaryStdOut.close();

    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] alphabet = new char[256];
        for (int i = 0; i < 256; i++) { // initializing alphabet values
            alphabet[i] = (char) i;
        }
        while (!BinaryStdIn.isEmpty()) {
            int c = BinaryStdIn.readChar();
            char buffer = alphabet[0];

            BinaryStdOut.write(alphabet[c]);

            for (int i = 1; i < c; i++) {
                char temp_buffer = alphabet[i];
                alphabet[i] = buffer;
                buffer = temp_buffer;
            }
        }

        BinaryStdIn.close();
        BinaryStdOut.close();

    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        MoveToFront.encode();

    }

}