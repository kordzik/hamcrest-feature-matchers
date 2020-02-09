package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.GenerateFeatureMatchers;
import com.github.kordzik.hamcrest.test.custompackage.byclass1.EntityInCustomPackageByClass11;
import com.github.kordzik.hamcrest.test.custompackage.byclass2.EntityInCustomPackageByClass21;

@GenerateFeatureMatchers(packagesEnclosing = {
        EntityInCustomPackageByClass11.class,
        EntityInCustomPackageByClass21.class
})
public class CustomPackageByClassMarker {
}
