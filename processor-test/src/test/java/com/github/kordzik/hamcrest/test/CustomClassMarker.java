package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.GenerateFeatureMatchers;
import com.github.kordzik.hamcrest.test.customclass.EntityCustomClassNoAnnotation;
import com.github.kordzik.hamcrest.test.customclass.EntityCustomClassWithAnnotation;

@GenerateFeatureMatchers(classes = {
        EntityCustomClassNoAnnotation.class,
        EntityCustomClassWithAnnotation.class
})
public class CustomClassMarker {
}
