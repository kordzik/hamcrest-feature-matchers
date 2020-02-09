package com.github.kordzik.hamcrest.test;

import static java.util.Objects.requireNonNull;

@CustomFeatures
public class EntitySimpleWithCustomAnnotation {

    private final long id;
    private final String name;
    private final boolean likely;

    public EntitySimpleWithCustomAnnotation(long id, String name, boolean likely) {
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
