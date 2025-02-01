package com.tester;

import java.io.DataInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClassFileVersionChecker {

    public static void main(String[] args) throws Exception {
        Files.walk(Path.of(args[0])).filter(p -> p.toString().endsWith(".class")).forEach(p -> {
            try {
                checkClassVersion(p);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        });
    }
    
    private static void checkClassVersion(Path path) throws Exception {
        try (DataInputStream in = new DataInputStream(Files.newInputStream(path))) {
            int magic = in.readInt();
            if (magic != 0xcafebabe) {
                throw new RuntimeException(path + " is not a valid class!");
            }
            int minor = in.readUnsignedShort();
            int major = in.readUnsignedShort();
            System.out.println(path + "," + major + "," + minor);
        }
    }

}
