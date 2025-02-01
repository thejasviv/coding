package com.tester;

public class JdwpThreadpoolTester {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                try {
                    System.out.println("Hello from " + Thread.currentThread().getId() + "," + Thread.currentThread().getName());
                    Thread.sleep(5000);
                } catch (Exception e) {}
            }).start();
            Thread.sleep(10000);
        }
    }

}
