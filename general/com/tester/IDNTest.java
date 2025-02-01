package com.tester;

import java.net.IDN;

public class IDNTest {

    public static void main(String[] args) {
        //String name = "aÅsk)jwf,ua...";
        //String name = "aÅsk)jwf,ua...";
        String name = "hello..";
        System.out.println(IDN.toASCII(name));
        //System.out.println(IDN.toUnicode(name));
    }

}
