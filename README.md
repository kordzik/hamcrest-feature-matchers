# Hamcrest Feature Matchers

TODO

# TODO

* proper name / package?
* tests for custom return types and all supported primitives
    * also manual tests to actually use the generated matchers
* output package - think through
    * customizable (THIS, MIRROR, CUSTOM)
    * check patterns in immutables
* inheritance from interfaces
    * research - JDK doesn't seem to support this
* inheritance customizable
    * inherit all
    * don't inherit
* support generic types? in particular should work with iterable matchers well
* custom method filter (annotation, visibility)
* composite feature methods (using @Feature)
* test error conditions - need to spawn compiler process inside the test?
* use javapoet to write source
* support nested static classes (generate inside top matcher class)
* support arrays and other type kinds?    
     