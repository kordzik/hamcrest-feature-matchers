package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.Features;

@Features
public interface EntityInterface {

    long getId();

    String getName();

    boolean isLikely();
}
