package com.github.kordzik.hamcrest.test.custompackage;

import com.github.kordzik.hamcrest.test.AbstractProcessorOutputTest;
import com.github.kordzik.hamcrest.test.custompackage.byclass1.EntityInCustomPackageByClass11;
import com.github.kordzik.hamcrest.test.custompackage.byclass1.EntityInCustomPackageByClass12;
import com.github.kordzik.hamcrest.test.custompackage.byclass2.EntityInCustomPackageByClass21;
import com.github.kordzik.hamcrest.test.custompackage.byclass2.EntityInCustomPackageByClass22;

import java.util.Set;

public class EntitiesInCustomPackageByClassOutputTest extends AbstractProcessorOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("id", "name", "likely");
    }

    @Override
    protected Set<Class<?>> featureClasses() {
        return Set.of(
                EntityInCustomPackageByClass11.class,
                EntityInCustomPackageByClass12.class,
                EntityInCustomPackageByClass21.class,
                EntityInCustomPackageByClass22.class
        );
    }
}
