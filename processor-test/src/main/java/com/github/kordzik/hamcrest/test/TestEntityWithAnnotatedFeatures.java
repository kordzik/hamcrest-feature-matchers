package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.Feature;
import com.github.kordzik.hamcrest.Features;

import static java.util.Objects.requireNonNull;

@Features
public class TestEntityWithAnnotatedFeatures {

    private final long id;
    private final String name;
    private final String foo;
    private final String bar;

    public TestEntityWithAnnotatedFeatures(long id, String name, String foo, String bar) {
        this.id = id;
        this.name = requireNonNull(name, "name");
        this.foo = foo;
        this.bar = bar;
    }

    @Feature
    public long getId() {
        return id;
    }

    @Feature
    public String getName() {
        return name;
    }

    public String getFoo() {
        return foo;
    }

    public String getBar() {
        return bar;
    }
}
