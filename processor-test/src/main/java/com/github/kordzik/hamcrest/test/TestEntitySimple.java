package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.Features;

import static java.util.Objects.requireNonNull;

@Features
public class TestEntitySimple {

    private final long id;
    private final String name;

    public TestEntitySimple(long id, String name) {
        this.id = id;
        this.name = requireNonNull(name, "name");
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
