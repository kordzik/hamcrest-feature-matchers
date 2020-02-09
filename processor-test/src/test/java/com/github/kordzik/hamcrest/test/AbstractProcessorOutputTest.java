package com.github.kordzik.hamcrest.test;

import io.vavr.CheckedConsumer;
import org.hamcrest.Matcher;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

import static com.github.kordzik.hamcrest.test.ProcessorTests.featureIsMethodName;
import static com.github.kordzik.hamcrest.test.ProcessorTests.featureMatchMethodName;
import static com.github.kordzik.hamcrest.test.ProcessorTests.featureMatcherClass;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.stream;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.apache.commons.lang3.ClassUtils.primitiveToWrapper;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractProcessorOutputTest {

    protected abstract Set<Class<?>> featureClasses();

    @ParameterizedTest
    @MethodSource("generatedFeatures")
    void testFeatureExists(String featureName) {
        featureClasses().forEach(CheckedConsumer.<Class<?>>of(fc -> testFeatureExists(fc, featureName)).unchecked());
    }

    @ParameterizedTest
    @MethodSource("javaBuiltInFeatures")
    void testJavaBuiltInDoesNotExist(String builtInName) {
        featureClasses().stream()
                .filter(not(Class::isInterface))
                .forEach(CheckedConsumer.<Class<?>>of(fc -> testFeatureDoesNotExist(fc, builtInName)).unchecked());
    }

    static Set<String> javaBuiltInFeatures() {
        return Set.of("toString", "hashCode", "class");
    }

    private static void testFeatureExists(Class<?> featureClass, String featureName) throws Exception {
        final var featureMatcherClass = featureMatcherClass(featureClass);
        final var featureMatchMethod = featureMatcherClass.getMethod(featureMatchMethodName(featureName), Matcher.class);
        assertTrue(isPublic(featureMatchMethod.getModifiers()));
        assertTrue(isStatic(featureMatchMethod.getModifiers()));

        Class<?> featureIsReturnType = primitiveToWrapper(findFeatureMethod(featureClass, featureName).getReturnType());
        Method featureIsMethod = featureMatcherClass.getMethod(featureIsMethodName(featureName),
                featureIsReturnType);
        assertTrue(isPublic(featureIsMethod.getModifiers()));
        assertTrue(isStatic(featureIsMethod.getModifiers()));
    }

    protected static void testFeatureDoesNotExist(Class<?> featureClass, String featureName) {
        // ensure the feature exists in feature class
        findFeatureMethod(featureClass, featureName);
        Set<String> methodNames = stream(featureMatcherClass(featureClass).getMethods())
                .map(Method::getName)
                .collect(toUnmodifiableSet());
        assertFalse(methodNames.contains(featureMatchMethodName(featureName)));
        assertFalse(methodNames.contains(featureIsMethodName(featureName)));
    }

    protected static Method findFeatureMethod(Class<?> featureClass, String featureName) {
        return findNoArgMethod(featureClass, featureName)
                .or(() -> findNoArgMethod(featureClass, "get" + capitalize(featureName)))
                .or(() -> findNoArgMethod(featureClass, "is" + capitalize(featureName)))
                .orElseThrow(() -> new AssertionError("Feature method not found: " + featureName));
    }

    protected static Optional<Method> findNoArgMethod(Class<?> targetClass, String name) {
        return stream(targetClass.getMethods())
                .filter(m -> m.getName().equals(name))
                .filter(m -> m.getParameterCount() == 0)
                .findFirst();
    }
}
