package com.github.kordzik.hamcrest;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import static com.github.kordzik.hamcrest.CodeConstants.FEATURE_MATCHER_CLASS_SUFFIX;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

final class FeatureMatcherClass {

    private final TypeElement featureClass;

    private final String name;
    private final String packageName;

    FeatureMatcherClass(TypeElement featureClass) {
        this.featureClass = requireNonNull(featureClass, "featureClass");
        this.name = initName();
        this.packageName = initPackageName();
    }

    public String getName() {
        return name;
    }

    public String getFqn() {
        return packageName + "." + name;
    }

    public String getPackageName() {
        return packageName;
    }

    private String initName() {
        return featureClass.getSimpleName() + FEATURE_MATCHER_CLASS_SUFFIX;
    }

    private String initPackageName() {
        Element enclosing = featureClass.getEnclosingElement();
        while (enclosing.getKind() != ElementKind.PACKAGE) {
            enclosing = enclosing.getEnclosingElement();
            if (enclosing == null) {
                throw new IllegalStateException(format("Unable to find package for '%s'", featureClass.getQualifiedName()));
            }
        }
        return ((PackageElement) enclosing).getQualifiedName().toString();
    }
}
