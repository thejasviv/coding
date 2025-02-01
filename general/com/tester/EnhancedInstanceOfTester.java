package com.tester;

import java.util.Objects;

public class EnhancedInstanceOfTester {

    private class MyObject {
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof MyObject myobj))
                return false;
            return myobj.equals(obj);
        }
    }
    
    public static void main(String[] args) {
        Object x = new Object();
        Object y = new Object();
        if (!(x instanceof Exception e))
            System.out.println("Not the instance");
        Objects.hash(x, y, new Object());
    }

}
