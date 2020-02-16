package com.github.kordzik.hamcrest.test;

import java.util.Set;

public class EntityWithFeatureNameClashOutputTest extends AbstractSingleClassProcessorOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("getName", "isName");
    }

    @Override
    protected Class<?> featureClass() {
        return EntityWithFeatureNameClash.class;
    }
}
