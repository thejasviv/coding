package com.tester;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class RecursiveDelete {

    public static void main(String[] args) throws Exception {
        Files.walk(Path.of(args[0])).map(Path::toFile).sorted(Comparator.reverseOrder()).forEachOrdered(File::delete);
    }

}
