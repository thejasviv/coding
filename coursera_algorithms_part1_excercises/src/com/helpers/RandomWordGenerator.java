package com.helpers;

public class RandomWordGenerator {

    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        for (int i = 0; i < count; i++) {
            int length = (int) (Math.random() * 10);
            char[] string = new char[length];
            for (int j = 0; j < length; j++) {
                System.out.print((char) (32 + Math.random() * 95));
            }
            System.out.println();
        }
    }

}
