package com.github.kordzik.hamcrest;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.io.PrintWriter;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

final class FeatureMatcherClassWriter extends AbstractCodeWriter {

    private static final String FEATURE_MATCHER_CLASS_SUFFIX = "Matchers";

    private final TypeElement featureClass;
    private final List<ExecutableElement> methods;
    private final PrintWriter writer;

    private final String packageName;
    private final String featureMatcherClassName;

    FeatureMatcherClassWriter(TypeElement featureClass, List<ExecutableElement> methods, PrintWriter writer) {
        this.featureClass = requireNonNull(featureClass, "featureClass");
        this.methods = requireNonNull(methods, "methods");
        this.writer = requireNonNull(writer, "printWriter");
        this.packageName = getPackageName();
        this.featureMatcherClassName = getFeatureMatcherClassName();
    }

    void write() {
        writePackageAndImports();
        writeClassOpening();
        methods.forEach(this::writeFeatureMethods);
        writeClassClosing();
    }

    private String getPackageName() {
        Element enclosing = featureClass.getEnclosingElement();
        while (enclosing.getKind() != ElementKind.PACKAGE) {
            enclosing = enclosing.getEnclosingElement();
            if (enclosing == null) {
                throw new IllegalStateException(format("Unable to find package for '%s'", featureClass.getQualifiedName()));
            }
        }
        return ((PackageElement) enclosing).getQualifiedName().toString();
    }

    private String getFeatureMatcherClassName() {
        return featureClass.getSimpleName() + FEATURE_MATCHER_CLASS_SUFFIX;
    }

    private void writePackageAndImports() {
        writer.printf("package %s;%n", packageName);
        writer.println();
        writeImport(CodeConstants.HAMCREST_MATCHER_CLASS);
        writer.println();
        writeImportStatic(CodeConstants.FEATURE_MATCHER_METHOD);
        writer.println();
    }

    private void writeClassOpening() {
        writer.printf("class %s {%n", featureMatcherClassName);
        writer.println();
        writer.printf("%sprivate %s() {}%n", tab(1), featureMatcherClassName);
        writer.println();
    }

    private void writeClassClosing() {
        writer.println("}");
    }

    private void writeFeatureMethods(ExecutableElement method) {
        new FeatureMatcherMethodWriter(method, writer).writeImports();
    }

    private void writeImport(String name) {
        writer.printf("import %s;%n", name);
    }

    private void writeImportStatic(String name) {
        writer.printf("import static %s;%n", name);
    }
}
