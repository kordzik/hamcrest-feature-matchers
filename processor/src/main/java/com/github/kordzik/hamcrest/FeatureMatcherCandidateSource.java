package com.github.kordzik.hamcrest;

import com.google.common.base.MoreObjects;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;
import java.util.List;
import java.util.Set;

import static com.github.kordzik.hamcrest.CodeConstants.ANNOTATED_WITH_PROPERTY;
import static com.github.kordzik.hamcrest.CodeConstants.CLASSES_PROPERTY;
import static com.github.kordzik.hamcrest.CodeConstants.PACKAGES_ENCLOSING_PROPERTY;
import static com.github.kordzik.hamcrest.CodeConstants.PACKAGES_PROPERTY;
import static com.github.kordzik.hamcrest.ElementUtils.getQualifiedName;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Sets.intersection;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toUnmodifiableSet;

final class FeatureMatcherCandidateSource {

    private final Element element;
    private final Elements elements;
    private final AnnotationMirror annotation;
    private final AnnotationAccessor annotationAccessor;

    FeatureMatcherCandidateSource(Elements elements, Element element, AnnotationMirror annotation) {
        requireNonNull(elements, "elements");
        requireNonNull(element, "element");
        requireNonNull(annotation, "annotation");
        checkArgument(element instanceof QualifiedNameable, "Unexpected element type: " + element.getClass());
        this.element = element;
        this.annotation = annotation;
        this.annotationAccessor = new AnnotationAccessor(elements, annotation);
        this.elements = elements;
    }

    Elements elements() {
        return elements;
    }

    Element getElement() {
        return element;
    }

    AnnotationMirror getAnnotation() {
        return annotation;
    }

    String getAnnotationQualifiedName() {
        return annotationAccessor.getQualifiedName();
    }

    String getAnnotationSimpleName() {
        return annotationAccessor.getSimpleName();
    }

    List<String> getPackages() {
        return annotationAccessor.getStringListValue(PACKAGES_PROPERTY);
    }

    List<DeclaredType> getPackagesEnclosing() {
        return annotationAccessor.getClassListValue(PACKAGES_ENCLOSING_PROPERTY);
    }

    List<DeclaredType> getAnnotatedWith() {
        return annotationAccessor.getClassListValue(ANNOTATED_WITH_PROPERTY);
    }

    List<DeclaredType> getClasses() {
        return annotationAccessor.getClassListValue(CLASSES_PROPERTY);
    }

    boolean isAnnotated(TypeElement element) {
        final Set<Element> supportedAnnotations = getAnnotatedWith().stream()
                .map(DeclaredType::asElement)
                .collect(toUnmodifiableSet());
        if (supportedAnnotations.isEmpty()) {
            // empty set means no annotation filter
            return true;
        }

        // compare elements and not declared types, as we are dealing with marker annotations, so the specifics are
        // irrelevant
        final Set<Element> allAnnotations = elements.getAllAnnotationMirrors(element).stream()
                .map(AnnotationMirror::getAnnotationType)
                .map(DeclaredType::asElement)
                .collect(toUnmodifiableSet());

        return !intersection(allAnnotations, supportedAnnotations).isEmpty();
    }

    boolean isDefaultPackageScan() {
        return getPackages().isEmpty() && getPackagesEnclosing().isEmpty() && getClasses().isEmpty();
     }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("element", getQualifiedName(element))
                .add("annotation", getAnnotationSimpleName())
                .toString();
    }
}
