package com.github.kordzik.hamcrest;

import com.google.common.base.Strings;

import java.io.PrintWriter;

import static com.github.kordzik.hamcrest.CodeConstants.JAVA_PACKAGE;
import static java.util.Objects.requireNonNull;

abstract class AbstractCodeWriter {

    static final int TAB_SIZE = 4;

    final PrintWriter writer;

    AbstractCodeWriter(PrintWriter writer) {
        this.writer = requireNonNull(writer, "writer");
    }

    void writeImport(String name) {
        writer.printf("import %s;%n", name);
    }

    void writeImportStatic(String name) {
        writer.printf("import static %s;%n", name);
    }

    static boolean isJavaPackage(String fqn) {
        return fqn.startsWith(JAVA_PACKAGE);
    }

    static String tab(int depth) {
        return Strings.repeat(" ", TAB_SIZE * depth);
    }
}
