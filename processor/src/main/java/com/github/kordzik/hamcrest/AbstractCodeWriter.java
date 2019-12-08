package com.github.kordzik.hamcrest;

import com.google.common.base.Strings;

import static com.github.kordzik.hamcrest.CodeConstants.JAVA_PACKAGE;

abstract class AbstractCodeWriter {

    static final int TAB_SIZE = 4;

    static boolean isJavaPackage(String fqn) {
        return fqn.startsWith(JAVA_PACKAGE);
    }

    static String tab(int depth) {
        return Strings.repeat(" ", TAB_SIZE * depth);
    }
}
