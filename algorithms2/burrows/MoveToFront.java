package algorithms2.burrows;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        int[] alphabet = new int[26];
        int A = 'A';
        for (int i = 0; i < 26; i++) {
            alphabet[i] = A + i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            for (int i = 0; i < 25; i++) {
                if (alphabet[i] == c) {
                    BinaryStdOut.write(i);
                    alphabet[0] = c;
                    break;
                }
                int buffer = alphabet[i+1];
                alphabet[i+1] = buffer;
            }
        }
        BinaryStdIn.close();
        BinaryStdOut.close();

    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int[] alphabet = new int[26];
        int A = 'A';
        for (int i = 0; i < 26; i++) {
            alphabet[i] = A + i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            for (int i = 0; i < 26; i++) {
                if (alphabet[i] == c) {
                    BinaryStdOut.write(i);
                    alphabet[0] = c;
                    break;
                }
                int buffer = alphabet[i+1];
                alphabet[i+1] = buffer;
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