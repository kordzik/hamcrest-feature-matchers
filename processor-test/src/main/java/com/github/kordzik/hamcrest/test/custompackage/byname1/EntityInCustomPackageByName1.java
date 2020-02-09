package com.github.kordzik.hamcrest.test.custompackage.byname1;

import com.github.kordzik.hamcrest.Features;

@Features
public class EntityInCustomPackageByName1 {

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
