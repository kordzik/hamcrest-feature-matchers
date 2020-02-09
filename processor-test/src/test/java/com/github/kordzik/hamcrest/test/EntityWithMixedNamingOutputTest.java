package com.github.kordzik.hamcrest.test;

import java.util.Set;

public class EntityWithMixedNamingOutputTest extends AbstractSingleClassProcessorOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("id", "name", "idNoGetter", "nameNoGetter");
    }

    @Override
    protected Class<?> featureClass() {
        return EntityWithMixedNaming.class;
    }
}
