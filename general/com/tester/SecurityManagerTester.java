package com.tester;

public class SecurityManagerTester {

    public static void main(String[] args) {
        System.setSecurityManager(new SandboxSecurityManager());
        SecurityManager manager = System.getSecurityManager();
        RuntimePermission perm = new RuntimePermission("hello");
        System.out.println(manager);
        manager.checkPermission(perm);
    }

}
