# Hamcrest Feature Matchers

TODO

# TODO

* proper name / package?
* output package - think through
    * customizable (THIS, MIRROR, CUSTOM)
    * check patterns in immutables
* support empty annotatedWith() - all classes in target packages
* migrate to customizing using separate annotations
  * FeatureMatcher.Source
  * FeatureMatcher.Target
  * FeatureMatcher.TypeFilter
  * FeatureMatcher.FeatureFilter    
* inheritance from interfaces
    * research - JDK doesn't seem to support this
* support generic types? in particular should work with iterable matchers well
* custom method filter (annotation, visibility)
* composite feature methods (using @Feature)
* inheritance customizable
    * inherit all
    * don't inherit
* test error conditions - need to spawn compiler process inside the test?
* use javapoet to write source
* support nested static classes (generate inside top matcher class)
* support arrays and other type kinds?    
     