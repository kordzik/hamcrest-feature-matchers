package com.github.kordzik.hamcrest.test;

import java.util.Set;

public class EntitiesWithAnnotatedFeaturesOutputTest extends AbstractProcessorSkippedOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("id", "name");
    }

    protected static Set<String> skippedFeatures() {
        return Set.of("foo", "bar");
    }

    @Override
    protected Set<Class<?>> featureClasses() {
        return Set.of(
                EntityWithAnnotatedFeatures.class,
                EntityInterfaceWithAnnotatedFeatures.class
        );
    }
}
