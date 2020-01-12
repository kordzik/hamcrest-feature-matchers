package com.github.kordzik.hamcrest.test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class AbstractProcessorSkippedOutputTest extends AbstractProcessorOutputTest {

    @ParameterizedTest
    @MethodSource("skippedFeatures")
    void testFeatureDoesNotExist(String featureName) {
        // ensure the feature exists in feature class
        findFeatureMethod(featureName);
        Set<String> methodNames = stream(featureMatcherClass().getMethods())
                .map(Method::getName)
                .collect(toUnmodifiableSet());
        assertFalse(methodNames.contains(featureMatchMethodName(featureName)));
        assertFalse(methodNames.contains(featureIsMethodName(featureName)));
    }
}
