package com.github.kordzik.hamcrest.test;

import java.util.Set;

public class EntitySimpleOutputTest extends AbstractProcessorOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("id", "name");
    }

    @Override
    protected Class<?> featureClass() {
        return EntitySimple.class;
    }
}
