package com.github.kordzik.hamcrest.test;

import java.util.Set;

public class EntityWithSimpleNameClashOutputTest extends AbstractSingleClassProcessorOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("getName", "name", "isLikely", "likely");
    }

    @Override
    protected Class<?> featureClass() {
        return EntityWithSimpleNameClash.class;
    }
}
