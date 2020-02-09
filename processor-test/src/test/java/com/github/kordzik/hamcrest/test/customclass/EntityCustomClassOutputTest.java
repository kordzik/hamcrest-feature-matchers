package com.github.kordzik.hamcrest.test.customclass;

import com.github.kordzik.hamcrest.test.AbstractProcessorOutputTest;

import java.util.Set;

public class EntityCustomClassOutputTest extends AbstractProcessorOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("id", "name", "likely");
    }

    @Override
    protected Set<Class<?>> featureClasses() {
        return Set.of(
                EntityCustomClassNoAnnotation.class,
                EntityCustomClassWithAnnotation.class
        );
    }
}
