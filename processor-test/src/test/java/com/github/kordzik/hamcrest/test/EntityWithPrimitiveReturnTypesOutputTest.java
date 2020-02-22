package com.github.kordzik.hamcrest.test;

import java.util.Set;

public class EntityWithPrimitiveReturnTypesOutputTest extends AbstractSingleClassProcessorOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("boolean", "char", "byte", "short", "int", "long", "float", "double");
    }

    @Override
    protected Class<?> featureClass() {
        return EntityWithPrimitiveReturnTypes.class;
    }
}
