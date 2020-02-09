package com.github.kordzik.hamcrest.test;

import io.vavr.CheckedConsumer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.Set;

import static com.github.kordzik.hamcrest.test.ProcessorTests.featureIsMethodName;
import static com.github.kordzik.hamcrest.test.ProcessorTests.featureMatchMethodName;
import static com.github.kordzik.hamcrest.test.ProcessorTests.featureMatcherClass;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class AbstractProcessorSkippedOutputTest extends AbstractProcessorOutputTest {

    @ParameterizedTest
    @MethodSource("skippedFeatures")
    void testFeatureDoesNotExist(String featureName) {
        featureClasses().forEach(CheckedConsumer.<Class<?>>of(fc -> testFeatureDoesNotExist(fc, featureName))
                .unchecked());
    }

    private static void testFeatureDoesNotExist(Class<?> featureClass, String featureName) {
        // ensure the feature exists in feature class
        findFeatureMethod(featureClass, featureName);
        Set<String> methodNames = stream(featureMatcherClass(featureClass).getMethods())
                .map(Method::getName)
                .collect(toUnmodifiableSet());
        assertFalse(methodNames.contains(featureMatchMethodName(featureName)));
        assertFalse(methodNames.contains(featureIsMethodName(featureName)));
    }
}
