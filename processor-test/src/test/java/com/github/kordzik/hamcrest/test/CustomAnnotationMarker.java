package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.GenerateFeatureMatchers;

@GenerateFeatureMatchers(annotatedWith = { CustomFeatures.class, CustomFeaturesInherited.class })
public class CustomAnnotationMarker {
}
