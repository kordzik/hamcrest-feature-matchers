package com.github.kordzik.matchers.api;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.function.Function;

public final class FeatureMatchers {

    private FeatureMatchers() {
    }

    public static <T, U> Matcher<T> featureIs(String name, Function<T, U> feature, U expectedValue) {
        return featureMatcher(name, feature, Matchers.is(expectedValue));
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
