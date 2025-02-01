package com.tester;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MethodHandlesLookupTest {
    
    private static class InnerClass {
        private static final int x = 10;
    }

    public static void main(String[] args) throws Exception {
        final var lookup = MethodHandles.privateLookupIn(Field.class, MethodHandles.lookup());
        final VarHandle MODIFIERS = lookup.findVarHandle(Field.class, "modifiers", int.class);
        Field field = InnerClass.class.getDeclaredField("x");
        final int mods = field.getModifiers();
        if (Modifier.isFinal(mods)) {
            MODIFIERS.set(field, mods & ~Modifier.FINAL);
        }

    }
}
