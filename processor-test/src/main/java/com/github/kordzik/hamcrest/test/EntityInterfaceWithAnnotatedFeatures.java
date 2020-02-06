package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.Feature;
import com.github.kordzik.hamcrest.Features;

@Features
public interface EntityInterfaceWithAnnotatedFeatures {

    @Feature
    long getId();

    @Feature
    String getName();

    String getFoo();

    String getBar();
}
