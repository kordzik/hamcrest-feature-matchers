package com.github.kordzik.hamcrest.test.customclass;

import com.github.kordzik.hamcrest.Features;

@Features
public class EntityCustomClassWithAnnotation {

    public long getId() {
        return 1;
    }

    public String getName() {
        return "name";
    }

    public boolean isLikely() {
        return true;
    }
}
