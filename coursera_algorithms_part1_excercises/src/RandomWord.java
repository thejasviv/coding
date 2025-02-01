/**
 * https://coursera.cs.princeton.edu/algs4/assignments/hello/specification.php
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = null;
        int i = 0;
        while (!StdIn.isEmpty()) {
            i++;
            String s = StdIn.readString();
            if (StdRandom.bernoulli(1.00 / i)) {
                champion = s;
            }
        }
        StdOut.println(champion);
    }
}
