package algorithms2.burrows;

import java.util.Arrays;

public class CircularSuffixArray {
    private String string;
    private String[] original;
    private String[] sorted;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();
        string = s;
        original = new String[string.length()];
        sorted = new String[string.length()];

        for (int i = 0; i < string.length(); i++) {
            StringBuilder st = new StringBuilder();
            for (int j = i; j < string.length(); j++) {
                st.append(string.charAt(j));
            }
            for (int j = 0; j < i; j++) {
                st.append(string.charAt(j));
            }
            original[i] = st.toString();
            sorted[i] = st.toString();
        }
        Arrays.sort(sorted);

    }

    // length of s
    public int length() {
        return string.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > string.length()-1) throw new IllegalArgumentException();

        String wanted_string = sorted[i];

        for (int j = 0; j < length(); j++) {
            if (original[j].equals(wanted_string)) return j;
        }
        throw new IllegalArgumentException();

    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray a = new CircularSuffixArray("ABRACADABRA!");
        System.out.println(a.index(11));


    }

}