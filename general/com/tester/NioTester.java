package com.tester;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

public class NioTester {

    public static void main(String[] args) throws IOException {
        Path root = Path.of("/Users/tvoniadka/ws/core-public/core");
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + root + "/build/*/*.properties");
        Files.walk(root, 3)
            .filter(matcher::matches)
            .forEach(System.out::println);
    }

}
