package com.tester;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RegExTester {

    public static void main(String[] args) {
        System.out.println("jdk.version = 1.2.3".matches("jdk\\.(runtime\\.)?version\\s*=.*"));
        System.getProperties().entrySet().forEach(e -> System.out.println(e.getKey() + "=" + e.getValue()));
        List<String> x = new ArrayList<>();
        List.of(1, 2)
            .stream()
            .map(RegExTester::getJDKVersions)
            .flatMap(s -> s)
            .forEach(x::add);
        for (String s : x) {
            System.out.println(s);
        }
    }
    
    private static Stream<String> getJDKVersions(int i) {
        return List.of("Kjhhdf", "Jhjjsdjg")
                .stream()
                .map(s -> "Hello " + s);
    }
}
