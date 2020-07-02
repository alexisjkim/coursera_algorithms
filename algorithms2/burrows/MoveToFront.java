
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] alphabet = new char[256];
        for (char i = 0; i < 256; i++) { // initializing alphabet values
            alphabet[i] = i;
        }

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            char buffer = alphabet[0];


            int x = 0;

            while (alphabet[x] != c) x++;
            char x_value = alphabet[x];


            BinaryStdOut.write((char) x);
            System.arraycopy(alphabet, 0, alphabet, 1, x);
            alphabet[0] = x_value;

        }

        BinaryStdIn.close();
        BinaryStdOut.close();

    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] alphabet = new char[256];
        for (char i = 0; i < 256; i++) { // initializing alphabet values
            alphabet[i] = i;
        }

        while (!BinaryStdIn.isEmpty()) {
            int c = BinaryStdIn.readChar();

            char c_value = alphabet[c];
            BinaryStdOut.write(alphabet[c]);

            System.arraycopy(alphabet, 0, alphabet, 1, c);
            alphabet[0] = c_value;


        }

        BinaryStdIn.close();
        BinaryStdOut.close();

    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) MoveToFront.encode();
        if (args[0].equals("+")) MoveToFront.decode();




    }

}