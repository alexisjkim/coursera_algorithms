package algorithms2.burrows;

public class CircularSuffixArray {
    private String string;
    private String[] original;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();
        string = s;
        original = new String[string.length()];

        for (int i = 0; i < string.length(); i++) {
            StringBuilder st = new StringBuilder();
            for (int j = string.length() - i; j < string.length(); j++) {
                st.append(string.charAt(j));
            }
            for (int j = 0; j < string.length() - i; j++) {
                st.append(string.charAt(j));
            }
            original[i] = st.toString();
        }

    }

    // length of s
    public int length() {
        return string.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > string.length()-1) throw new IllegalArgumentException();


        return 0;
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray a = new CircularSuffixArray("ABRACADABRA!");


    }

}