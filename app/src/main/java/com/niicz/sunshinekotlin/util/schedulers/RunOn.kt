package com.niicz.sunshinekotlin.util.schedulers

import javax.inject.Qualifier


/**
 * Qualifier to define Scheduler type (io, computation, or ui main thread).
 */
@Qualifier
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class RunOn(val value: SchedulerType = SchedulerType.IO)
