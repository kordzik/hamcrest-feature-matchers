package com.github.kordzik.hamcrest;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.PrintWriter;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

final class FeatureMatcherMethodWriter extends AbstractCodeWriter {

    private static final String GETTER_PREFIX = "get";

    private final ExecutableElement method;
    private final PrintWriter writer;

    private final String featureName;
    private final String featureTypeFqn;
    private final String featureTypeSimple;

    FeatureMatcherMethodWriter(ExecutableElement method, PrintWriter writer) {
        this.method = requireNonNull(method, "method");
        this.writer = requireNonNull(writer, "writer");

        this.featureName = getFeatureName();
        this.featureTypeFqn = getFeatureType();
        this.featureTypeSimple = featureTypeFqn.substring(featureTypeFqn.lastIndexOf('.'));
    }

    private String getFeatureName() {
        final String methodName = method.getSimpleName().toString();
        return methodName.startsWith(GETTER_PREFIX) ?
                uncapitalize(methodName.substring(GETTER_PREFIX.length())) :
                methodName;
    }

    private String getFeatureType() {
        final TypeMirror returnType = method.getReturnType();
        return returnType.getKind().isPrimitive() ? getBoxedType(returnType.getKind()) : getTypeFqn(returnType);
    }

    private String getBoxedType(TypeKind kind) {
        switch (kind) {
            case INT:
                return Integer.class.getName();
            case LONG:
                return Long.class.getName();
                // TODO
            default:
                throw new AssertionError("Kind not resolved to boxed: " + kind);
        }
    }

    private String getTypeFqn(TypeMirror returnType) {
        final TypeElement typeElement = (TypeElement) ((DeclaredType) returnType).asElement();
        return typeElement.getQualifiedName().toString();
    }

    void writeImports() {
        // TODO
    }

    void writeMethods() {
        writeFeatureIsMethod();
        writeFeatureMatchMethod();
    }

    private void writeFeatureIsMethod() {
        // TODO
    }

    private void writeFeatureMatchMethod() {
        writer.printf("%spublic static with%sThat(Matcher<%s> %sMatcher)",
                tab(1),
                capitalize(featureName),
                featureTypeSimple,
                featureName);
        // TODO
    }
}
