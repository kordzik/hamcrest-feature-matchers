package com.github.kordzik.hamcrest;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.github.kordzik.hamcrest.CodeConstants.FEATURES_ANNOTATION;
import static java.lang.String.format;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toUnmodifiableList;

@SupportedAnnotationTypes(FEATURES_ANNOTATION)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class FeatureMatcherProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        var grouped = annotations.stream()
                .flatMap(a -> roundEnv.getElementsAnnotatedWith(a).stream())
                .collect(groupingBy(FeatureMatcherProcessor::partition));
        // errors
        Optional.ofNullable(grouped.get(InternalAnnotatedElement.OTHER))
                .ifPresent(others -> others.forEach(this::logError));
        // classes
        Optional.ofNullable(grouped.get(InternalAnnotatedElement.CLASS))
                .ifPresent(classes -> classes.forEach(this::processClass));
        // methods
        Optional.ofNullable(grouped.get(InternalAnnotatedElement.METHOD))
                .ifPresent(methods -> methods.forEach(this::processMethod));

        return true;
    }

    private static InternalAnnotatedElement partition(Element element) {
        switch (element.getKind()) {
            case CLASS:
                return InternalAnnotatedElement.CLASS;
            case METHOD:
                return InternalAnnotatedElement.METHOD;
            default:
                return InternalAnnotatedElement.OTHER;
        }
    }

    private void logError(Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                "Unexpected element annotated with " + FEATURES_ANNOTATION);
    }

    private void processClass(Element annotatedClass) {
        try {
            validateClass((TypeElement) annotatedClass);
            processValidClass((TypeElement) annotatedClass);
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }

    private void validateClass(TypeElement annotatedClass) {
        // only top-level and static nested classes
        if (annotatedClass.getNestingKind().isNested()) {
            throw new IllegalStateException(format("'%s' is nested", annotatedClass.getQualifiedName()));
        }
    }

    private void processValidClass(TypeElement annotatedClass) {
        final List<ExecutableElement> candidateMethods = annotatedClass.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.METHOD)
                .map(ExecutableElement.class::cast)
                .filter(FeatureMatcherProcessor::isPublic)
                .filter(not(FeatureMatcherProcessor::isStatic))
                .filter(m -> m.getParameters().isEmpty())
                .filter(FeatureMatcherProcessor::isEligibleReturnType)
                .collect(toUnmodifiableList());
        if (candidateMethods.isEmpty()) {
            throw new IllegalStateException(format("%s has no feature methods", annotatedClass.getQualifiedName()));
        }


    }

    private void processMethod(Element annotatedMethod) {
        // TODO
    }

    private static boolean isPublic(Element element) {
        return element.getModifiers().contains(Modifier.PUBLIC);
    }

    private static boolean isStatic(Element element) {
        return element.getModifiers().contains(Modifier.STATIC);
    }

    private static boolean isEligibleReturnType(ExecutableElement candidateMethod) {
        return candidateMethod.getReturnType().getKind().isPrimitive() ||
                candidateMethod.getReturnType().getKind() == TypeKind.DECLARED;
    }

    private enum InternalAnnotatedElement {
        CLASS,
        METHOD,
        OTHER
    }
}
