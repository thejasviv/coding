package com.tester;

public class JFRTester {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            callMyMethod1();
            Thread.sleep(10000);
        }
    }
    
    private static void callMyMethod1() {
        System.out.println("Called 1");
        callMyMethod2();
    }

    private static void callMyMethod2() {
        System.out.println("Called 2");
    }
}
