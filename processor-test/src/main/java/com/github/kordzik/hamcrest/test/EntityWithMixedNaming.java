package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.Features;

/**
 * Getters and no-getters, mixed.
 */
@Features
public class EntityWithMixedNaming {

    public long getId() {
        return 1;
    }

    public long idNoGetter() {
        return 2;
    }

    public String getName() {
        return "name";
    }

    public String nameNoGetter() {
        return "nameNoGetter";
    }
}
