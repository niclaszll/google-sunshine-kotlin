package com.niicz.sunshinekotlin.data

import android.app.Application
import com.niicz.sunshinekotlin.data.repository.WeatherRepository
import com.niicz.sunshinekotlin.di.AppModule
import com.niicz.sunshinekotlin.util.SharedPrefsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class),
    (WeatherRepositoryModule::class),
    (NetworkModule::class),
    (SharedPrefsModule::class)])
interface WeatherRepositoryComponent {

    fun provideWeatherRepository(): WeatherRepository

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): WeatherRepositoryComponent.Builder

        fun build(): WeatherRepositoryComponent
    }
}