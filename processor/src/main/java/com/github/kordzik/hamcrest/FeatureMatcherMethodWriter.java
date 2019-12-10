package com.github.kordzik.hamcrest;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.PrintWriter;

import static com.github.kordzik.hamcrest.CodeConstants.FEATURE_MATCHER_METHOD;
import static com.github.kordzik.hamcrest.CodeConstants.HAMCREST_MATCHER_CLASS;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

final class FeatureMatcherMethodWriter extends AbstractCodeWriter {

    private static final String GETTER_PREFIX = "get";

    private final ExecutableElement method;

    private final String methodName;
    private final String classNameSimple;
    private final String featureName;
    private final String featureTypeFqn;
    private final String featureTypeSimple;

    FeatureMatcherMethodWriter(ExecutableElement method, PrintWriter writer) {
        super(writer);
        this.method = requireNonNull(method, "method");

        this.methodName = getMethodName();
        this.classNameSimple = getClassNameSimple();
        this.featureName = getFeatureName();
        this.featureTypeFqn = getFeatureType();
        this.featureTypeSimple = featureTypeFqn.substring(featureTypeFqn.lastIndexOf('.'));
    }

    private String getMethodName() {
        return method.getSimpleName().toString();
    }

    private String getClassNameSimple() {
        final Element classElement = method.getEnclosingElement();
        checkState(classElement instanceof TypeElement,
                "Expected method enclosing element to be a class, but was: %s", classElement);
        return classElement.getSimpleName().toString();
    }


    private String getFeatureName() {
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
            case FLOAT:
                return Float.class.getName();
            case DOUBLE:
                return Double.class.getName();
            case SHORT:
                return Short.class.getName();
            case BYTE:
                return Byte.class.getName();
            case BOOLEAN:
                return Boolean.class.getName();
            case CHAR:
                return Character.class.getName();
            default:
                throw new AssertionError("Kind unsupported - not resolved to boxed: " + kind);
        }
    }

    private String getTypeFqn(TypeMirror returnType) {
        final TypeElement typeElement = (TypeElement) ((DeclaredType) returnType).asElement();
        return typeElement.getQualifiedName().toString();
    }

    void writeImports() {
        if (!isJavaPackage(featureTypeFqn)) {
            writeImport(featureTypeFqn);
        }
    }

    void writeMethods() {
        writeFeatureIsMethod();
        writeFeatureMatchMethod();
    }

    private void writeFeatureIsMethod() {
        final String expectedVarName = format("expected%s", capitalize(featureName));

        writer.printf("%spublic static with%s(%s %s) {",
                tab(1),
                capitalize(featureName),
                featureTypeSimple,
                expectedVarName);
        writer.printf("%sreturn with%sThat(is(%s));%n",
                tab(2),
                capitalize(featureName),
                expectedVarName);
        writer.printf("%s}%n", tab(1));
        writer.println();
    }

    private void writeFeatureMatchMethod() {
        final String matcherVarName = format("%s%s", featureName, HAMCREST_MATCHER_CLASS);

        writer.printf("%spublic static with%sThat(%s<%s> %s) {",
                tab(1),
                capitalize(featureName),
                HAMCREST_MATCHER_CLASS,
                featureTypeSimple,
                matcherVarName);
        writer.printf("%sreturn %s(\"%s\", %s::%s, %s);%n",
                tab(2),
                FEATURE_MATCHER_METHOD,
                featureName,
                classNameSimple,
                methodName,
                matcherVarName);
        writer.printf("%s}%n", tab(1));
        writer.println();
    }
}
