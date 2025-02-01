package com.tester;

public class SystemPropertyTester {

    public static void main(String[] args) {
        System.getProperties().forEach((k, v) -> System.out.println(k + "=" + v));
    }

}
