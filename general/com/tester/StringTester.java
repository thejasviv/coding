package com.tester;

public class StringTester {

    public static void main(String[] args) {
        String str = String.format("\0\0\0%c%s", 8, "hello");
        byte[] b = str.getBytes();
        System.out.println(b);
    }

}
