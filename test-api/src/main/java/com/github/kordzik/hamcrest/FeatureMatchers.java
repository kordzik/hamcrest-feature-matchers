package com.github.kordzik.hamcrest;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.function.Function;

import static org.hamcrest.Matchers.is;

public final class FeatureMatchers {

    private FeatureMatchers() {
    }

    public static <T, U> Matcher<T> featureIs(String name, Function<T, U> feature, U expectedValue) {
        return featureMatcher(name, feature, is(expectedValue));
    }

    public static <T, U> Matcher<T> featureMatcher(String name, Function<T, U> feature, Matcher<U> nestedMatcher) {
        return new FeatureMatcher<>(nestedMatcher, name, name) {
            @Override
            protected U featureValueOf(T actual) {
                return feature.apply(actual);
            }
        };
    }
}
