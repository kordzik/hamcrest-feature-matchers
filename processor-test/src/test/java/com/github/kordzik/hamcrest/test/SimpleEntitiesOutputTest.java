package com.github.kordzik.hamcrest.test;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.github.kordzik.hamcrest.test.ProcessorTests.featureMatcherClass;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleEntitiesOutputTest extends AbstractProcessorOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("id", "name", "likely");
    }

    @Override
    protected Set<Class<?>> featureClasses() {
        return Set.of(
                EntitySimple.class,
                EntitySimpleInTests.class,
                EntityInterface.class
        );
    }

    @Test
    void shouldNotGeneratedForInheritingClass() {
        assertThrows(ClassNotFoundException.class, () -> featureMatcherClass(EntityInterfaceImpl.class));
    }
}
