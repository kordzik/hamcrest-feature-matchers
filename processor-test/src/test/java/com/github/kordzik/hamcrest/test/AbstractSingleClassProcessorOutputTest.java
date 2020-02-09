package com.github.kordzik.hamcrest.test;

import java.util.Set;

public abstract class AbstractSingleClassProcessorOutputTest extends AbstractProcessorOutputTest {

    @Override
    protected final Set<Class<?>> featureClasses() {
        return Set.of(featureClass());
    }

    protected abstract Class<?> featureClass();
}
