package com.github.kordzik.hamcrest.test;

import io.vavr.control.Try;

import static com.github.kordzik.hamcrest.CodeConstants.FEATURE_MATCHER_CLASS_SUFFIX;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.capitalize;

final class ProcessorTests {

    private static final String FEATURE_MATCH_METHOD_TEMPLATE = "with%sThat";
    private static final String FEATURE_IS_METHOD_TEMPLATE = "with%s";
    
    private ProcessorTests() {
    }

    protected static Class<?> featureMatcherClass(Class<?> featureClass) {
        return Try.of(() -> Class.forName(featureClass.getName() + FEATURE_MATCHER_CLASS_SUFFIX)).get();
    }

    protected static String featureMatchMethodName(String featureName) {
        return format(FEATURE_MATCH_METHOD_TEMPLATE, capitalize(featureName));
    }

    protected static String featureIsMethodName(String featureName) {
        return format(FEATURE_IS_METHOD_TEMPLATE, capitalize(featureName));
    }
}
