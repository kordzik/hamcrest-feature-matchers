package com.github.kordzik.hamcrest;

import com.google.common.base.MoreObjects;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableList;

final class FeatureMatcherCandidate {

    private final FeatureMatcherCandidateSource source;
    private final TypeElement type;
    private final FeatureMatcherClass featureMatcherClass;
    private final List<ExecutableElement> methods;

    FeatureMatcherCandidate(FeatureMatcherCandidateSource source, TypeElement type, List<ExecutableElement> methods) {
        this.source = requireNonNull(source, "source");
        this.type = requireNonNull(type, "type");
        this.methods = List.copyOf(methods);
        this.featureMatcherClass = new FeatureMatcherClass(type);
    }

    static FeatureMatcherCandidate fromType(FeatureMatcherCandidateSource source, TypeElement type) {
        final var members = source.elements().getAllMembers(type);
        final var allCandidateMethods = ElementFilter.methodsIn(members).stream()
                .filter(ElementUtils::isPublic)
                .filter(not(ElementUtils::isStatic))
                .filter(m -> m.getParameters().isEmpty())
                .filter(FeatureMatcherCandidate::isEligibleReturnType)
                .filter(not(ElementUtils::isJavaBuiltInType))
                .collect(toUnmodifiableList());
        final var annotatedCandidateMethods = allCandidateMethods.stream()
                .filter(m -> m.getAnnotation(Feature.class) != null)
                .collect(toUnmodifiableList());

        final var methods  = annotatedCandidateMethods.isEmpty() ? allCandidateMethods : annotatedCandidateMethods;
        return new FeatureMatcherCandidate(source, type, methods);
    }

    FeatureMatcherCandidateSource getSource() {
        return source;
    }

    TypeElement getType() {
        return type;
    }

    List<ExecutableElement> getMethods() {
        return methods;
    }

    FeatureMatcherClass getFeatureMatcherClass() {
        return featureMatcherClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeatureMatcherCandidate)) {
            return false;
        }
        FeatureMatcherCandidate that = (FeatureMatcherCandidate) o;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("source", source)
                .add("type", type.getQualifiedName())
                .add("methods", methods.stream().map(ExecutableElement::getSimpleName).collect(toUnmodifiableList()))
                .toString();
    }

    private static boolean isEligibleReturnType(ExecutableElement candidateMethod) {
        return candidateMethod.getReturnType().getKind().isPrimitive() ||
                candidateMethod.getReturnType().getKind() == TypeKind.DECLARED;
    }
}
