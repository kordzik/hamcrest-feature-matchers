package com.github.kordzik.hamcrest;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.stream.Collectors.toUnmodifiableList;

final class AnnotationAccessor {

    private final Elements elements;
    private final AnnotationMirror annotation;

    AnnotationAccessor(Elements elements, AnnotationMirror annotation) {
        this.elements = elements;
        this.annotation = annotation;
    }

    String getQualifiedName() {
        return ElementUtils.getQualifiedName(annotation);
    }

    String getSimpleName() {
        return annotation.getAnnotationType().asElement().getSimpleName().toString();
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String property) {
        final ExecutableElement method = ElementUtils.getAnnotationProperties(annotation).get(property);
        checkArgument(method != null, format("No such property in %s: '%s'", getQualifiedName(), property));
        return (T) elements.getElementValuesWithDefaults(annotation).get(method).getValue();
    }

    public List<String> getStringListValue(String property) {
        return getListValue(property, String.class);
    }

    public List<DeclaredType> getClassListValue(String property) {
        return getListValue(property, DeclaredType.class);
    }

    private <E> List<E> getListValue(String property, Class<E> elementType) {
        List<AnnotationValue> values = getValue(property);
        return values.stream()
                .map(AnnotationValue::getValue)
                .map(elementType::cast)
                .collect(toUnmodifiableList());
    }
}
