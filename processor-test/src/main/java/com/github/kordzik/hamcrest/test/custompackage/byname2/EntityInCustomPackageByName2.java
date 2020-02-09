package com.github.kordzik.hamcrest.test.custompackage.byname2;

import com.github.kordzik.hamcrest.Features;

@Features
public class EntityInCustomPackageByName2 {

    public long getId() {
        return 1;
    }

    public String getName() {
        return "name";
    }

    public boolean isLikely() {
        return true;
    }
}
