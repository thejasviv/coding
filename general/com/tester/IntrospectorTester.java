package com.tester;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;

public class IntrospectorTester {

    public interface IFace {
        default String getDefaultString() {
            return null;
        }
        String getNonDefaultString();
    }

    public class Impl implements IFace {
        @Override
        public String getDefaultString() {
            return "Hello";
        }
        @Override
        public String getNonDefaultString() {
            return "Non-Default";
        }
    }

    public static void main(String[] args) throws IntrospectionException {
        BeanInfo info = Introspector.getBeanInfo(Impl.class);
        for (PropertyDescriptor desc : info.getPropertyDescriptors()) {
            System.out.println(desc.getReadMethod());
        }
        for (MethodDescriptor desc : info.getMethodDescriptors()) {
            System.out.println(desc);
        }
    }
}
