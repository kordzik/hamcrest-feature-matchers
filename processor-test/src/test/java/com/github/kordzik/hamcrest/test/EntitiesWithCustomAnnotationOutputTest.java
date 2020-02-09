package com.github.kordzik.hamcrest.test;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.github.kordzik.hamcrest.test.ProcessorTests.featureMatcherClass;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntitiesWithCustomAnnotationOutputTest extends AbstractProcessorOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("id", "name", "likely");
    }

    @Override
    protected Set<Class<?>> featureClasses() {
        return Set.of(
                EntitySimpleWithCustomAnnotation.class,
                EntityInterfaceWithCustomAnnotation.class,

                EntitySimpleWithCustomInheritedAnnotation.class,
                EntitySimpleWithCustomInheritedAnnotationExt.class,
                EntityInterfaceWithCustomInheritedAnnotation.class
        );
    }

    @Test
    void shouldNotGenerateForExtendingClass() {
        assertThrows(ClassNotFoundException.class, () -> featureMatcherClass(EntitySimpleWithCustomAnnotationExt.class));
    }

    @Test
    void shouldNotGenerateForInterfaceImplementations() {
        assertThrows(ClassNotFoundException.class, () -> featureMatcherClass(EntityInterfaceWithCustomAnnotationImpl.class));
        assertThrows(ClassNotFoundException.class, () -> featureMatcherClass(EntityInterfaceWithCustomInheritedAnnotationImpl.class));
    }
}
