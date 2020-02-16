package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.Features;

@Features
public class EntityWithSimpleNameClash {

    public String getName() {
        return "getName";
    }

    public String name() {
        return "name";
    }

    public boolean isLikely() {
        return true;
    }

    public boolean likely() {
        return false;
    }
}
