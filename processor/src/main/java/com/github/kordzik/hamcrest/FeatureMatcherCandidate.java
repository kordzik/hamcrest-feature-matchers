package com.github.kordzik.hamcrest;

import com.google.common.base.MoreObjects;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
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

    public static FeatureMatcherCandidate fromType(FeatureMatcherCandidateSource source, TypeElement type) {
        // TODO
        return null;
    }

    public FeatureMatcherCandidateSource getSource() {
        return source;
    }

    public TypeElement getType() {
        return type;
    }

    public List<ExecutableElement> getMethods() {
        return methods;
    }

    public FeatureMatcherClass getFeatureMatcherClass() {
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
}
