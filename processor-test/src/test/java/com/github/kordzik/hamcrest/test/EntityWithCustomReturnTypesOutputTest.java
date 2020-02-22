package com.github.kordzik.hamcrest.test;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.github.kordzik.hamcrest.test.EntitySimpleMatchers.withId;
import static com.github.kordzik.hamcrest.test.EntitySimpleMatchers.withLikely;
import static com.github.kordzik.hamcrest.test.EntitySimpleMatchers.withName;
import static com.github.kordzik.hamcrest.test.EntityWithCustomReturnTypesMatchers.withEntitySimpleThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;

public class EntityWithCustomReturnTypesOutputTest extends AbstractSingleClassProcessorOutputTest {

    protected static Set<String> generatedFeatures() {
        return Set.of("entitySimple");
    }

    @Override
    protected Class<?> featureClass() {
        return EntityWithCustomReturnTypes.class;
    }

    @Test
    void nestedMatcherUsage() {
        final var entity = new EntityWithCustomReturnTypes();
        assertThat(entity, withEntitySimpleThat(allOf(withId(1L), withName("name"), withLikely(true))));
    }
}
