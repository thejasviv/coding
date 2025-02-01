package com.tester;

import java.util.List;

public class StreamTester {

    public static void main(String[] args) {
        List<Integer> list = List.of(10, 20, 30);
        list.stream().map(i -> i + 10).toList();
    }

}
