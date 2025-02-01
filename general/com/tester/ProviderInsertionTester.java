package com.tester;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.Set;

public class ProviderInsertionTester {

    public static void main(String[] args) {
        Provider provider = new Provider("MyCustom", "1.0.0", "My Custom Provider") {
            @Override
            public Set<Provider.Service> getServices() {
                return Set.of(new Provider.Service(Security.getProviders()[0], getVersionStr(), getName(), getInfo(), null, null));
            }
        };
        Security.insertProviderAt(provider, 1);
        Arrays.stream(Security.getProviders()).forEach(p -> p.getServices().forEach(System.out::println));
    }
    
    private class MyCustomProvider extends Provider {
        MyCustomProvider() {
            super("MyCustom", "1.0.0", "My Custom Provider");
        }
    }
}
