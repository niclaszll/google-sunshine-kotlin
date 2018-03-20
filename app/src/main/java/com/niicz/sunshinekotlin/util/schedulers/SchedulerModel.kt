package com.niicz.sunshinekotlin.util.schedulers

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Provides common Schedulers used by RxJava
 */
@Module
class SchedulerModel {

    @Provides
    @RunOn(SchedulerType.IO)
    fun provideIO(): Scheduler{
        return Schedulers.io()
    }

    @Provides
    @RunOn(SchedulerType.UI)
    fun provideUI(): Scheduler{
        return AndroidSchedulers.mainThread()
    }
}