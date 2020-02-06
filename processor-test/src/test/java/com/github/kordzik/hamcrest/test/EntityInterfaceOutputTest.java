package com.github.kordzik.hamcrest.test;

import java.util.Set;

public class EntityInterfaceOutputTest extends AbstractProcessorOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("id", "name", "likely");
    }

    @Override
    protected Class<?> featureClass() {
        return EntityInterface.class;
    }
}
