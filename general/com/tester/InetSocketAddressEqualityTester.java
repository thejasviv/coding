package com.tester;

import java.net.InetSocketAddress;

import com.google.common.base.Objects;

import junit.framework.Assert;

public class InetSocketAddressEqualityTester {

    public static void main(String[] args) {
        InetSocketAddress addr1 = new InetSocketAddress("http://hello", 20001);
        InetSocketAddress addr2 = new InetSocketAddress("http://hello", 20001);
        System.out.println(Objects.equal(addr1, addr2));
        Assert.assertEquals(addr1,  addr2);
    }

}
