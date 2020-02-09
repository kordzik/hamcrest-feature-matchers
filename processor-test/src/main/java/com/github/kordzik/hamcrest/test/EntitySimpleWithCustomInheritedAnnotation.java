package com.github.kordzik.hamcrest.test;

import static java.util.Objects.requireNonNull;

@CustomFeaturesInherited
public class EntitySimpleWithCustomInheritedAnnotation {

    private final long id;
    private final String name;
    private final boolean likely;

    public EntitySimpleWithCustomInheritedAnnotation(long id, String name, boolean likely) {
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
