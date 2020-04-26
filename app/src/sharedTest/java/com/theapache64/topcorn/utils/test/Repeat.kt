package com.theapache64.topcorn.utils.test

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
annotation class Repeat(val value: Int = 1)