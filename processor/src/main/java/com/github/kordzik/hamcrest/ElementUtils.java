package com.github.kordzik.hamcrest;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.QualifiedNameable;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableList;

final class ElementUtils {

    private ElementUtils() {
    }

    public static String getQualifiedName(Element element) {
        return ((QualifiedNameable) element).getQualifiedName().toString();
    }

    public static String getQualifiedName(AnnotationMirror annotation) {
        return getQualifiedName(annotation.getAnnotationType().asElement());
    }

    public static List<ExecutableElement> getMethods(Element typeElement) {
        return typeElement.getEnclosedElements().stream()
                .filter(ExecutableElement.class::isInstance)
                .map(ExecutableElement.class::cast)
                .collect(toUnmodifiableList());
    }

    public static Map<String, ExecutableElement> getAnnotationProperties(AnnotationMirror annotation) {
        return getMethods(annotation.getAnnotationType().asElement()).stream()
                .collect(toImmutableMap(e -> e.getSimpleName().toString(), identity()));
    }
}
