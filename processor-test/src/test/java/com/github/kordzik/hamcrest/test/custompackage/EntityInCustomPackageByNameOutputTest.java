package com.github.kordzik.hamcrest.test.custompackage;

import com.github.kordzik.hamcrest.test.AbstractProcessorOutputTest;
import com.github.kordzik.hamcrest.test.custompackage.byname1.EntityInCustomPackageByName1;
import com.github.kordzik.hamcrest.test.custompackage.byname2.EntityInCustomPackageByName2;

import java.util.Set;

public class EntityInCustomPackageByNameOutputTest extends AbstractProcessorOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("id", "name", "likely");
    }

    @Override
    protected Set<Class<?>> featureClasses() {
        return Set.of(EntityInCustomPackageByName1.class, EntityInCustomPackageByName2.class);
    }
}
