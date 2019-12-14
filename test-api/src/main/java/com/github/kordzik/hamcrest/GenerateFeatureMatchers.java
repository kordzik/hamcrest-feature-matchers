package com.github.kordzik.hamcrest;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PACKAGE, ElementType.TYPE})
public @interface GenerateFeatureMatchers {

    String[] packages() default {};

    Class<?>[] packagesEnclosing() default {};

    Class<? extends Annotation>[] annotatedWith() default { Features.class };

    Class<?>[] classes() default {};
}
