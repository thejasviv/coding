package com.tester;

public interface UiView {
    default String getDefaultString() {
        return null;
    }

    String getNonDefaultString();
}
