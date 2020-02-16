package com.github.kordzik.hamcrest;

import javax.lang.model.element.ExecutableElement;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

final class FeatureMatcherWriteContext {

    private static final String GETTER_PREFIX = "get";
    private static final String IS_PREFIX = "is";

    private final FeatureMatcherClass featureMatcherClass;
    private final Map<String, ExecutableElement> methods;

    public FeatureMatcherWriteContext(FeatureMatcherClass featureMatcherClass, List<ExecutableElement> methods) {
        this.featureMatcherClass = requireNonNull(featureMatcherClass, "featureMatcherClass");
        requireNonNull(methods, "methods");
        this.methods = methods.stream().collect(toUnmodifiableMap(m -> m.getSimpleName().toString(), identity()));
    }

    public String getFeatureName(String methodName) {
        if (methodName.startsWith(GETTER_PREFIX)) {
            return getFeatureName(GETTER_PREFIX, methodName, IS_PREFIX);
        } else if (methodName.startsWith(IS_PREFIX)) {
            return getFeatureName(IS_PREFIX, methodName, GETTER_PREFIX);
        } else {
            return methodName;
        }
    }

    public boolean hasMethod(String methodName) {
        return methods.containsKey(methodName);
    }

    private String getFeatureName(String prefix, String methodName, String... otherPrefixes) {
        final var featureName = uncapitalize(methodName.substring(prefix.length()));
        return noClash(featureName, otherPrefixes) ? featureName : methodName;
    }

    private boolean noClash(String featureName, String... prefixes) {
        return Stream.concat(Stream.of(featureName), stream(prefixes).map(p -> p + capitalize(featureName)))
                .noneMatch(this::hasMethod);
    }
}
