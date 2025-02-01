package com.tester;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class JdkPathValidator {
    
    private static final long WAIT_TIME_SECONDS = 10L;

    public static void main(String[] args) throws IOException, InterruptedException {
        new JdkPathValidator().validateJdk(args[0]);
    }
    
    private void validateJdk(String root) throws IOException, InterruptedException {
        for (Path jdkPath : getJDKPaths(Path.of(root), System.getProperty("os.arch"))) {
            if (!runJDK(jdkPath)) {
                throw new IllegalStateException("Failed to validate " + jdkPath);
            }
        }
    }
    
    private Set<Path> getJDKPaths(Path root, String arch) throws IOException {
        Path jdkRoot = root.resolve("../").toAbsolutePath();

        Set<Path> jdkPaths = new HashSet<>();

        // JDK versions from YD files
        Path path = root.resolve("yd/sfdc-base-jdk.xml");
        Files.lines(path)
            .filter(l -> l.contains("openjdk"))
            .forEach(l -> jdkPaths.add(jdkRoot.resolve(l.split("/")[3])));

        // JDK versions from default.properties
        getJDKVersion(root.resolve("build/default.properties"), arch)
            .forEach(System.out::println);

        // JDK versions from pod override files
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + root + "/build/*/*.properties");
        Files.walk(root, 2)
            .filter(matcher::matches)
            .map(p -> getJDKVersion(p, arch))
            .flatMap(s -> s)
            .filter(s -> s != null)
            .map(jdkRoot::resolve)
            .forEach(jdkPaths::add);

        return jdkPaths;
    }

    private Stream<String> getJDKVersion(Path path, String arch) {
        try {
            return Files.lines(path)
                    .filter(l -> l.matches("jdk\\.(runtime\\.)?version\\s*=.*"))
                    .map(l -> l.split("=")[1].trim().replace("${jdk.arch}", arch));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean runJDK(Path jdkPath) throws InterruptedException, IOException {
        return new ProcessBuilder()
                .command(List.of(jdkPath.resolve("bin/java").toAbsolutePath().toString(), "-version"))
                .inheritIO()
                .start()
                .waitFor(WAIT_TIME_SECONDS, TimeUnit.SECONDS);
    }

}
