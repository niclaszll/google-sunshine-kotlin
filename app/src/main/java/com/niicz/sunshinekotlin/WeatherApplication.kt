package com.niicz.sunshinekotlin

import android.app.Application
import android.content.Context
import com.niicz.sunshinekotlin.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


/**
 * We create a custom [Application] class that extends  [DaggerApplication].
 * We then override applicationInjector() which tells Dagger how to make our @Singleton Component
 * We never have to call `component.inject(this)` as [DaggerApplication] will do that for us.
 */
class WeatherApplication : DaggerApplication() {

    init {
        instance = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    //for shared prefs in non activity
    companion object {
        private var instance: WeatherApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }


}