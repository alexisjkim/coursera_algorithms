package algorithms2.wordnet;

public class Outcast {
    private WordNet net;
    public Outcast(WordNet wordnet) {
        net = wordnet;
    }     // constructor takes a WordNet object

    public String outcast(String[] nouns)  {

        String ans = nouns[0];
        int ans_dist = -1;

        for (int i = 0; i < nouns.length; i++) {
            int current_dist = 0;

            for (int j = 0; j < nouns.length; j++) {

                int x = net.distance(nouns[i], nouns[j]);
                current_dist += x;

            }
            if (ans_dist == -1 || current_dist > ans_dist) {
                ans_dist = current_dist;
                ans = nouns[i];
            }
        }

        return ans;
    } // given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {


    }
}