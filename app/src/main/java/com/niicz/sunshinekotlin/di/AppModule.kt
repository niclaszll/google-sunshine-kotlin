package com.niicz.sunshinekotlin.di

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import com.niicz.sunshinekotlin.network.FetchWeatherTask
import dagger.Binds
import dagger.Module
import dagger.Provides


/**
 * This is a Dagger module. We use this to bind our Application class as a Context in the AppComponent
 * By using Dagger Android we do not need to pass our Application instance to any module,
 * we simply need to expose our Application as Context.
 * One of the advantages of Dagger.Android is that your
 * Application & Activities are provided into your graph for you.
 * [ ].
 */
@Module
abstract class AppModule {
    //expose Application as an injectable context
    @Binds
    abstract fun bindContext(application: Application): Context
}