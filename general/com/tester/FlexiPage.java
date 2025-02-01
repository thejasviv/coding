package com.tester;

public class FlexiPage implements UiView {    

    @Override
    public String getDefaultString() {
        return "Hello";
    }

    @Override
    public String getNonDefaultString() {
        return "Non-Default";
    }
}
