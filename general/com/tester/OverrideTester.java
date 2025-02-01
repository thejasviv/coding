package com.tester;

import java.security.Provider;
import java.security.Security;

public class OverrideTester {
    
    private static class MyProvider extends Provider {
        
        Provider delegate = Security.getProvider("");

        protected MyProvider(String name, String versionStr, String info) {
            super(name, versionStr, info);
            // TODO Auto-generated constructor stub
        }

        @Override
        public boolean containsKey(Object key) {
            // TODO Auto-generated method stub
            return delegate.containsKey(key);
        }

        
        
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
