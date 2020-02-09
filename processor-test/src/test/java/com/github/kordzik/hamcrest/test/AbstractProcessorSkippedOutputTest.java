package com.github.kordzik.hamcrest.test;

import io.vavr.CheckedConsumer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public abstract class AbstractProcessorSkippedOutputTest extends AbstractProcessorOutputTest {

    @ParameterizedTest
    @MethodSource("skippedFeatures")
    void testFeatureDoesNotExist(String featureName) {
        featureClasses().forEach(CheckedConsumer.<Class<?>>of(fc -> testFeatureDoesNotExist(fc, featureName))
                .unchecked());
    }
}
