package com.github.kordzik.hamcrest;

import com.google.auto.service.AutoService;
import com.google.common.collect.Streams;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.github.kordzik.hamcrest.CodeConstants.GENERATE_ANNOTATION;
import static com.github.kordzik.hamcrest.ElementUtils.getPackage;
import static com.github.kordzik.hamcrest.ElementUtils.getQualifiedName;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static java.lang.String.format;

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
        if (source.isDefaultPackageScan()) {
            final var packageFromElement = Stream.of(getPackage(source.getElement()));
            return processCandidatesInPackages(packageFromElement, source);
        } else {
            return Streams.concat(candidatesFromPackages(source),
                    candidatesFromPackagesEnclosing(source),
                    candidatesFromClasses(source));
        }
    }

    private Stream<FeatureMatcherCandidate> candidatesFromPackages(FeatureMatcherCandidateSource source) {
        final Stream<PackageElement> packages = source.getPackages().stream()
                .flatMap(p -> processingEnv.getElementUtils().getAllPackageElements(p).stream());
        return processCandidatesInPackages(packages, source);
    }

    private Stream<FeatureMatcherCandidate> candidatesFromPackagesEnclosing(FeatureMatcherCandidateSource source) {
        final var packages = source.getPackagesEnclosing().stream()
                .map(DeclaredType::asElement)
                .map(ElementUtils::getPackage);
        return processCandidatesInPackages(packages, source);
    }

    private Stream<FeatureMatcherCandidate> processCandidatesInPackages(Stream<PackageElement> packages, FeatureMatcherCandidateSource source) {
        return packages
                .flatMap(p -> p.getEnclosedElements().stream())
                .map(TypeElement.class::cast)
                .filter(source::isAnnotated)
                .map(t -> FeatureMatcherCandidate.fromType(source, t));
    }

    private Stream<FeatureMatcherCandidate> candidatesFromClasses(FeatureMatcherCandidateSource source) {
        return source.getClasses().stream()
                .map(DeclaredType::asElement)
                .map(TypeElement.class::cast)
                .map(t -> FeatureMatcherCandidate.fromType(source, t));
    }

    private FeatureMatcherCandidate discardAndLog(FeatureMatcherCandidate first, FeatureMatcherCandidate second) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
                "Duplicate feature matcher candidate encountered and will be discarded : " + second);
        return first;
    }

    private void writeMatcher(FeatureMatcherCandidate candidate) {
        if (candidate.getMethods().isEmpty()) {
            logError("%s has no methods to generate feature matchers for.", getQualifiedName(candidate.getType()));
            return;
        }

        try {
            JavaFileObject classFile = processingEnv.getFiler().createSourceFile(candidate.getFeatureMatcherClass().getFqn());
            try(var writer = new PrintWriter(classFile.openWriter())) {
                new FeatureMatcherClassWriter(candidate, writer).write();
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }

    }

    private void logError(String msg, Object... args) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, format(msg, args));
    }
}
