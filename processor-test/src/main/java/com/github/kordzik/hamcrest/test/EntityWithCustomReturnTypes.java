package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.Features;

@Features
public class EntityWithCustomReturnTypes {

    public EntitySimple getEntitySimple() {
        return new EntitySimple(1, "name", true);
    }
}
