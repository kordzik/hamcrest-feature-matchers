package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.Feature;

import static java.util.Objects.requireNonNull;

@Feature
public class TestEntity {

    private final long id;
    private final String name;

    public TestEntity(long id, String name) {
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
