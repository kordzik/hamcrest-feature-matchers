package com.github.kordzik.hamcrest.test;

import io.vavr.control.Try;
import org.hamcrest.Matcher;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.Optional;

import static com.github.kordzik.hamcrest.CodeConstants.FEATURE_MATCHER_CLASS_SUFFIX;
import static java.lang.String.format;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.ClassUtils.primitiveToWrapper;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractProcessorOutputTest {

    private static final String FEATURE_MATCH_METHOD_TEMPLATE = "with%sThat";
    private static final String FEATURE_IS_METHOD_TEMPLATE = "with%s";

    protected abstract Class<?> featureClass();

    @ParameterizedTest
    @MethodSource("generatedFeatures")
    void testFeatureExists(String featureName) throws Exception {
        Method featureMatchMethod = featureMatcherClass().getMethod(featureMatchMethodName(featureName), Matcher.class);
        assertTrue(isPublic(featureMatchMethod.getModifiers()));
        assertTrue(isStatic(featureMatchMethod.getModifiers()));

        Class<?> featureIsReturnType = primitiveToWrapper(findFeatureMethod(featureName).getReturnType());
        Method featureIsMethod = featureMatcherClass().getMethod(featureIsMethodName(featureName),
                featureIsReturnType);
        assertTrue(isPublic(featureIsMethod.getModifiers()));
        assertTrue(isStatic(featureIsMethod.getModifiers()));
    }

    protected final Method findFeatureMethod(String featureName) {
        return findNoArgMethod(featureClass(), featureName)
                .or(() -> findNoArgMethod(featureClass(), "get" + capitalize(featureName)))
                .or(() -> findNoArgMethod(featureClass(), "is" + capitalize(featureName)))
                .orElseThrow(() -> new AssertionError("Feature method not found: " + featureName));
    }

    protected final Class<?> featureMatcherClass() {
        return Try.of(() -> Class.forName(this.featureClass().getName() + FEATURE_MATCHER_CLASS_SUFFIX)).get();
    }

    protected static Optional<Method> findNoArgMethod(Class<?> targetClass, String name) {
        return stream(targetClass.getMethods())
                .filter(m -> m.getName().equals(name))
                .filter(m -> m.getParameterCount() == 0)
                .findFirst();
    }

    protected static String featureMatchMethodName(String featureName) {
        return format(FEATURE_MATCH_METHOD_TEMPLATE, capitalize(featureName));
    }

    protected static String featureIsMethodName(String featureName) {
        return format(FEATURE_IS_METHOD_TEMPLATE, capitalize(featureName));
    }
}
