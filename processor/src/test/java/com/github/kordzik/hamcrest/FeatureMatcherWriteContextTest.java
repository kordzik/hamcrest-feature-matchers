package com.github.kordzik.hamcrest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.List;

import static com.github.kordzik.hamcrest.mock.MockName.name;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeatureMatcherWriteContextTest {

    @Mock
    private TypeElement featureType;
    @Mock
    private PackageElement featurePackage;
    @Mock
    private ExecutableElement method1;
    @Mock
    private ExecutableElement method2;

    private FeatureMatcherClass featureMatcherClass;

    @BeforeEach
    void initFeatureMatcherClass() {
        when(featurePackage.getKind()).thenReturn(ElementKind.PACKAGE);
        when(featurePackage.getQualifiedName()).thenReturn(name("com.github.kordzik.hamcrest"));

        when(featureType.getSimpleName()).thenReturn(name("TestEntity"));
        when(featureType.getEnclosingElement()).thenReturn(featurePackage);
        featureMatcherClass = new FeatureMatcherClass(featureType);

        when(method1.getSimpleName()).thenReturn(name("method1"));
        when(method2.getSimpleName()).thenReturn(name("method2"));
    }

    @Nested
    class HasMethod {

        @Test
        void shouldCalculateHasMethodBasedOnMethodNames() {
            final var context = createWriteContext();

            assertTrue(context.hasMethod("method1"));
            assertTrue(context.hasMethod("method2"));

            assertFalse(context.hasMethod("method3"));
            assertFalse(context.hasMethod("nosuchmethod"));
        }
    }

    @Nested
    class GetFeatureName {

        @Test
        void withoutPrefix() {
            assertEquals("myFeature", createWriteContext().getFeatureName("myFeature"));
        }

        @Test
        void withGetPrefixNoClashes() {
            assertEquals("myFeature", createWriteContext().getFeatureName("getMyFeature"));
        }

        @Test
        void withGetPrefixSimpleClash() {
            when(method1.getSimpleName()).thenReturn(name("myFeature"));

            assertEquals("getMyFeature", createWriteContext().getFeatureName("getMyFeature"));
        }

        @Test
        void withGetPrefixIsClash() {
            when(method1.getSimpleName()).thenReturn(name("isMyFeature"));

            assertEquals("getMyFeature", createWriteContext().getFeatureName("getMyFeature"));
        }

        @Test
        void withIsPrefixNoClashes() {
            assertEquals("myFeature", createWriteContext().getFeatureName("isMyFeature"));
        }

        @Test
        void withIsPrefixSimpleClash() {
            when(method1.getSimpleName()).thenReturn(name("myFeature"));

            assertEquals("isMyFeature", createWriteContext().getFeatureName("isMyFeature"));
        }

        @Test
        void withIsPrefixGetClash() {
            when(method1.getSimpleName()).thenReturn(name("getMyFeature"));

            assertEquals("isMyFeature", createWriteContext().getFeatureName("isMyFeature"));
        }
    }

    private FeatureMatcherWriteContext createWriteContext() {
        return new FeatureMatcherWriteContext(featureMatcherClass, List.of(method1, method2));
    }
}