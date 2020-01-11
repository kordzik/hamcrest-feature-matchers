package com.github.kordzik.hamcrest;

import javax.lang.model.element.ExecutableElement;
import java.io.PrintWriter;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toUnmodifiableList;

final class FeatureMatcherClassWriter extends AbstractCodeWriter {

    private final FeatureMatcherClass featureMatcherClass;
    private final List<FeatureMatcherMethodWriter> methodWriters;

    FeatureMatcherClassWriter(FeatureMatcherCandidate candidate, PrintWriter writer) {
        this(candidate.getFeatureMatcherClass(), candidate.getMethods(), writer);
    }

    FeatureMatcherClassWriter(FeatureMatcherClass featureMatcherClass, List<ExecutableElement> methods, PrintWriter writer) {
        super(writer);
        this.featureMatcherClass = requireNonNull(featureMatcherClass, "featureClass");
        this.methodWriters = requireNonNull(methods, "methods").stream()
                .map(m -> new FeatureMatcherMethodWriter(m, writer))
                .collect(toUnmodifiableList());
    }

    void write() {
        writePackageAndImports();
        writeClassOpening();
        methodWriters.forEach(FeatureMatcherMethodWriter::writeMethods);
        writeClassClosing();
    }

    private void writePackageAndImports() {
        writer.printf("package %s;%n", featureMatcherClass.getPackageName());
        writer.println();
        writeImport(CodeConstants.HAMCREST_MATCHER_CLASS_FQN);
        methodWriters.forEach(FeatureMatcherMethodWriter::writeImports);
        writer.println();
        writeImportStatic(CodeConstants.FEATURE_MATCHER_METHOD_FQN);
        writeImportStatic(CodeConstants.HAMCREST_MATCHERS_IS_METHOD_FQN);
        writer.println();
    }

    private void writeClassOpening() {
        writer.printf("public final class %s {%n", featureMatcherClass.getName());
        writer.println();
        writer.printf("%sprivate %s() {}%n", tab(1), featureMatcherClass.getName());
        writer.println();
    }

    private void writeClassClosing() {
        writer.println("}");
    }
}
