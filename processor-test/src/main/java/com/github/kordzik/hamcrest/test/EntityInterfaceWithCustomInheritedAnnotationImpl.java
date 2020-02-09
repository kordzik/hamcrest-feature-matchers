package com.github.kordzik.hamcrest.test;

public class EntityInterfaceWithCustomInheritedAnnotationImpl implements EntityInterfaceWithCustomInheritedAnnotation {

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
