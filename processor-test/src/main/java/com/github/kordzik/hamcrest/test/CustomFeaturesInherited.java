package com.github.kordzik.hamcrest.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.CLASS;

@Retention(CLASS)
@Target(ElementType.TYPE)
@Inherited
public @interface CustomFeaturesInherited {
}
