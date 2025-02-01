package com.tester;

public class ClassLoaderHierarchyTester {
    public static void main(String[] args) {
        printHierarchy(ClassLoaderHierarchyTester.class.getClassLoader());
    }
    private static void printHierarchy(ClassLoader loader) {
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(ClassLoader.getPlatformClassLoader());
        while (loader != null) {
            System.out.println(loader);
            loader = loader.getParent();
        }
    }
}
