package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.Features;

import static java.util.Objects.requireNonNull;

@Features
public class EntitySimple {

    private final long id;
    private final String name;
    private final boolean likely;

    public EntitySimple(long id, String name, boolean likely) {
        this.id = id;
        this.name = requireNonNull(name, "name");
        this.likely = likely;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isLikely() {
        return likely;
    }
}
