package com.github.kordzik.hamcrest;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableList;

final class ElementUtils {

    private ElementUtils() {
    }

    static String getQualifiedName(Element element) {
        return ((QualifiedNameable) element).getQualifiedName().toString();
    }

    static String getQualifiedName(AnnotationMirror annotation) {
        return getQualifiedName(annotation.getAnnotationType().asElement());
    }

    static PackageElement getPackage(Element element) {
        requireNonNull(element, "element");
        if (element instanceof PackageElement) {
            return (PackageElement) element;
        } else if (element instanceof TypeElement) {
            return (PackageElement) getTopLevel((TypeElement) element).getEnclosingElement();
        } else {
            throw new IllegalArgumentException("Unexpected element: " + element);
        }
    }

    static TypeElement getTopLevel(TypeElement type) {
        requireNonNull(type, "type");
        while (type.getNestingKind().isNested()) {
            type = (TypeElement) type.getEnclosingElement();
        }
        return type;
    }

    static List<ExecutableElement> getMethods(Element typeElement) {
        return typeElement.getEnclosedElements().stream()
                .filter(ExecutableElement.class::isInstance)
                .map(ExecutableElement.class::cast)
                .collect(toUnmodifiableList());
    }

    static Map<String, ExecutableElement> getAnnotationProperties(AnnotationMirror annotation) {
        return getMethods(annotation.getAnnotationType().asElement()).stream()
                .collect(toImmutableMap(e -> e.getSimpleName().toString(), identity()));
    }

    static boolean isPublic(Element element) {
        return element.getModifiers().contains(Modifier.PUBLIC);
    }

    static boolean isStatic(Element element) {
        return element.getModifiers().contains(Modifier.STATIC);
    }
}
