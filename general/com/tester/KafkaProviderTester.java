package com.tester;

import java.security.Security;
import java.util.Arrays;

import org.apache.kafka.common.security.scram.internals.ScramSaslClientProvider;

public class KafkaProviderTester {

    public static void main(String[] args) {
        ScramSaslClientProvider.initialize();
        Arrays.stream(Security.getProviders()).forEach(p -> p.getServices().forEach(System.out::println));
    }

}
