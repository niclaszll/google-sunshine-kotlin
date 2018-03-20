package com.niicz.sunshinekotlin.di

import android.app.Application
import com.niicz.sunshinekotlin.WeatherApplication
import com.niicz.sunshinekotlin.data.room.RoomModule
import com.niicz.sunshinekotlin.data.NetworkModule
import com.niicz.sunshinekotlin.data.WeatherRepositoryModule
import com.niicz.sunshinekotlin.util.SharedPrefsModule
import com.niicz.sunshinekotlin.util.schedulers.SchedulerModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
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
        (RoomModule::class),
        (SchedulerModel::class),
        (WeatherRepositoryModule::class)]
)
interface AppComponent : AndroidInjector<WeatherApplication> {

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