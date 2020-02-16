package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.Features;

@Features
public class EntityWithFeatureNameClash {

    public String getName() {
        return "getName";
    }

    public boolean isName() {
        return true;
    }
}
