package com.niicz.sunshinekotlin

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.niicz.sunshinekotlin.di.DaggerAppComponent


/**
 * We create a custom [Application] class that extends  [DaggerApplication].
 * We then override applicationInjector() which tells Dagger how to make our @Singleton Component
 * We never have to call `component.inject(this)` as [DaggerApplication] will do that for us.
 */
class WeatherApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }


}