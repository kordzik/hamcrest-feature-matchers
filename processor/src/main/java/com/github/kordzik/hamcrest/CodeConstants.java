package com.github.kordzik.hamcrest;

final class CodeConstants {

    private CodeConstants() {
    }

    static final String API_PACKAGE = "com.github.kordzik.hamcrest";
    static final String GENERATE_ANNOTATION = API_PACKAGE + ".GenerateFeatureMatchers";

    static final String PACKAGES_PROPERTY = "packages";
    static final String PACKAGES_ENCLOSING_PROPERTY = "packagesEnclosing";
    static final String ANNOTATED_WITH_PROPERTY = "annotatedWith";
    static final String CLASSES_PROPERTY = "classes";

    static final String FEATURE_MATCHERS_CLASS = "FeatureMatchers";
    static final String FEATURE_MATCHERS_CLASS_FQN = API_PACKAGE + "." + FEATURE_MATCHERS_CLASS;
    static final String FEATURE_MATCHER_METHOD = "featureMatcher";
    static final String FEATURE_MATCHER_METHOD_FQN = FEATURE_MATCHERS_CLASS_FQN + "." + FEATURE_MATCHER_METHOD;
    static final String FEATURE_MATCHER_CLASS_SUFFIX = "Matchers";

    static final String HAMCREST_PACKAGE = "org.hamcrest";
    static final String HAMCREST_MATCHER_CLASS = "Matcher";
    static final String HAMCREST_MATCHER_CLASS_FQN = HAMCREST_PACKAGE + "." + HAMCREST_MATCHER_CLASS;
    static final String HAMCREST_MATCHERS_CLASS = "Matchers";
    static final String HAMCREST_MATCHERS_CLASS_FQN = HAMCREST_PACKAGE + "." + HAMCREST_MATCHERS_CLASS;
    static final String HAMCREST_MATCHERS_IS_METHOD = "is";
    static final String HAMCREST_MATCHERS_IS_METHOD_FQN = HAMCREST_MATCHERS_CLASS_FQN + "." + HAMCREST_MATCHERS_IS_METHOD;

    static final String JAVA_PACKAGE = "java.";
}
