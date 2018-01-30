package com.niicz.sunshinekotlin.di

import com.niicz.sunshinekotlin.detail.DetailActivity
import com.niicz.sunshinekotlin.detail.DetailModule
import com.niicz.sunshinekotlin.forecast.ForecastActivity
import com.niicz.sunshinekotlin.forecast.ForecastModule
import com.niicz.sunshinekotlin.network.FetchWeatherTask
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityBindingModule is on,
 * in our case that will be AppComponent. The beautiful part about this setup is that you never need to tell AppComponent that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the specified modules and be aware of a scope annotation @ActivityScoped
 * When Dagger.Android annotation processor runs it will create 4 subcomponents for us.
 */
@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [(ForecastModule::class)])
    abstract fun forecastActivity(): ForecastActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(DetailModule::class)])
    abstract fun detailActivity(): DetailActivity

}