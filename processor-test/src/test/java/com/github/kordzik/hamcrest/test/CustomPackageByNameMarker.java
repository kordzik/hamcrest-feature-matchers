package com.github.kordzik.hamcrest.test;

import com.github.kordzik.hamcrest.GenerateFeatureMatchers;

@GenerateFeatureMatchers(packages = {
        "com.github.kordzik.hamcrest.test.custompackage.byname1",
        "com.github.kordzik.hamcrest.test.custompackage.byname2"
})
public class CustomPackageByNameMarker {
}
