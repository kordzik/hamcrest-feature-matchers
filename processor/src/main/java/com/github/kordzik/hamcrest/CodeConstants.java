package com.github.kordzik.hamcrest;

final class CodeConstants {

    static final String API_PACKAGE = "com.github.kordzik.hamcrest";
    static final String FEATURES_ANNOTATION = API_PACKAGE + ".Feature";

    static final String FEATURE_MATCHERS_CLASS = "FeatureMatchers";
    static final String FEATURE_MATCHERS_CLASS_FQN = API_PACKAGE + "." + FEATURE_MATCHERS_CLASS;
    static final String FEATURE_MATCHER_METHOD = "featureMatcher";
    static final String FEATURE_MATCHER_METHOD_FQN = FEATURE_MATCHERS_CLASS_FQN + "." + FEATURE_MATCHER_METHOD;

    static final String HAMCREST_PACKAGE = "org.hamcrest";
    static final String HAMCREST_MATCHER_CLASS = "Matcher";
    static final String HAMCREST_MATCHER_CLASS_FQN = HAMCREST_PACKAGE + "." + HAMCREST_MATCHER_CLASS;
    static final String HAMCREST_MATCHERS_CLASS = "Matchers";
    static final String HAMCREST_MATCHERS_CLASS_FQN = HAMCREST_PACKAGE + "." + HAMCREST_MATCHERS_CLASS;
    static final String HAMCREST_MATCHERS_IS_METHOD = "is";
    static final String HAMCREST_MATCHERS_IS_METHOD_FQN = HAMCREST_MATCHERS_CLASS_FQN + "." + HAMCREST_MATCHERS_IS_METHOD;

    static final String JAVA_PACKAGE = "java.";
}
