package com.github.kordzik.hamcrest.test;

import java.util.Set;

public class EntityInterfaceWithAnnotatedFeaturesOutputTest extends AbstractProcessorSkippedOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("id", "name");
    }

    protected static Set<String> skippedFeatures() {
        return Set.of("foo", "bar");
    }

    @Override
    protected Class<?> featureClass() {
        return EntityInterfaceWithAnnotatedFeatures.class;
    }
}
