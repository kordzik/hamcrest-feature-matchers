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
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.github.kordzik.hamcrest.CodeConstants.GENERATE_ANNOTATION;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static java.lang.String.format;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableList;

@SupportedAnnotationTypes(GENERATE_ANNOTATION)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class FeatureMatcherProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        var candidates = annotations.stream()
                .flatMap(a -> roundEnv.getElementsAnnotatedWith(a).stream())
                .flatMap(e -> e.getAnnotationMirrors().stream()
                        .map(a -> new FeatureMatcherCandidateSource(processingEnv.getElementUtils(), e, a)))
                .filter(c -> c.getAnnotationQualifiedName().equals(GENERATE_ANNOTATION))
                .flatMap(this::findCandidates)
                .collect(toImmutableMap(FeatureMatcherCandidate::getType, Function.identity(), this::discardAndLog));
        candidates.values().forEach(this::writeMatcher);

        return true;
    }

    private Stream<FeatureMatcherCandidate> findCandidates(FeatureMatcherCandidateSource source) {
        // TODO collect / scan from source using annotation values
        return null;
    }

    private FeatureMatcherCandidate discardAndLog(FeatureMatcherCandidate first, FeatureMatcherCandidate second) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
                "Duplicate feature matcher candidate encountered, will be discarded : " + second);
        return first;
    }

    private void logError(Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                "Unexpected element annotated with " + GENERATE_ANNOTATION);
    }

    private void writeMatcher(FeatureMatcherCandidate candidate) {
        try {
            JavaFileObject classFile = processingEnv.getFiler().createSourceFile(candidate.getFeatureMatcherClass().getFqn());
            try(var writer = new PrintWriter(classFile.openWriter())) {
                new FeatureMatcherClassWriter(candidate, writer).write();
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }

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

    private void processValidClass(TypeElement annotatedClass) throws IOException {
        var candidateMethods = annotatedClass.getEnclosedElements().stream()
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

        var featureMatcherClass = new FeatureMatcherClass(annotatedClass);
        var classFile = processingEnv.getFiler().createSourceFile(featureMatcherClass.getFqn());
        try(var writer = new PrintWriter(classFile.openWriter())) {
            new FeatureMatcherClassWriter(featureMatcherClass, candidateMethods, writer).write();
        }
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
}
