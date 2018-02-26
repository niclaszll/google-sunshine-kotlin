package com.niicz.sunshinekotlin.di

import android.app.Application
import android.arch.persistence.room.RoomDatabase
import android.content.SharedPreferences
import com.niicz.sunshinekotlin.WeatherApplication
import com.niicz.sunshinekotlin.data.LocationDao
import com.niicz.sunshinekotlin.data.WeatherDao
import com.niicz.sunshinekotlin.data.WeatherDatabase
import com.niicz.sunshinekotlin.network.FetchWeatherTask
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import javax.inject.Singleton


/**
 * This is a Dagger component. Refer to [WeatherApplication] for the list of Dagger components
 * used in this application.
 *
 *
 * Even though Dagger allows annotating a [Component] as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in [WeatherApplication].
 * [AndroidSupportInjectionModule] is the module from Dagger.Android that helps with the generation
 * and location of subcomponents.
 */
@Singleton
@Component(
    modules =
    [(AppModule::class),
        (ActivityBindingModule::class),
        (AndroidSupportInjectionModule::class),
        (NetworkModule::class),
        (SharedPrefsModule::class),
        (RoomModule::class)]
)
interface AppComponent : AndroidInjector<WeatherApplication> {

    fun provideSharedPreferences(): SharedPreferences
    fun provideOkhttpClient(): OkHttpClient
    fun provideFWT(): FetchWeatherTask
    fun provideRoomDatabase(): WeatherDatabase
    fun provideWeatherDao(): WeatherDao
    fun provideLocationDao(): LocationDao

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}