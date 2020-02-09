package com.github.kordzik.hamcrest.test;

public class EntityInterfaceWithCustomAnnotationImpl implements EntityInterfaceWithCustomAnnotation {

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getName() {
        return "name";
    }

    @Override
    public boolean isLikely() {
        return true;
    }
}
