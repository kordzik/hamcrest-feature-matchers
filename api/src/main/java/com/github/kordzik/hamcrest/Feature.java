package com.github.kordzik.hamcrest;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Documented
@Retention(SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Feature {

    boolean gettersOnly() default true;

    String[] includeIn() default {};
}
