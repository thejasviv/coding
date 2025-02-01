/**
 * https://coursera.cs.princeton.edu/algs4/assignments/hello/specification.php
 */

public class HelloGoodbye {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please provide at least 2 program arguments");
        } else {
            System.out.println("Hello " + args[0] + " and " + args[1] + ".");
            System.out.println("Goodbye " + args[1] + " and " + args[0] + ".");
        }
    }

}
