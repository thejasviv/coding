package com.tester;

import java.lang.reflect.Method;
import java.net.InetAddress;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatchers;

public class ByteBuddyTester {
   
   public static class InetAddressInterceptor {
       
       private final InetAddress address;

       InetAddressInterceptor(InetAddress address) {
           this.address = address;
       }

       @RuntimeType
       public Object delegate(
               @Origin Method method,
               @AllArguments Object... args) throws Exception {

           if (method.getName().equals("getHostName")) {
               return address.getHostAddress();
           }

           return method.invoke(address, args);
       }
   }
   
   public static void main(String[] args) throws Exception {
       System.out.println(createNonreversible("google.com"));
   }
   
   private static Class<? extends InetAddress> createNonreversible(String ip) throws Exception {
       if(ip == null || ip.isEmpty()) {
           throw new Exception("Ip address provided was null or empty.");
       }
       final InetAddress address = InetAddress.getByName(ip);
       Class<? extends InetAddress> proxyClass = new ByteBuddy()
               .subclass(InetAddress.class, ConstructorStrategy.Default.NO_CONSTRUCTORS)
               .method(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class)))
               .intercept(MethodDelegation.to(new InetAddressInterceptor(address)))
               .make()
               .load(ByteBuddyTester.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
               .getLoaded();
       return proxyClass;
   }
}
