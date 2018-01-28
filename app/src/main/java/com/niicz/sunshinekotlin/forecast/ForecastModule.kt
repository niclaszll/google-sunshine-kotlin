package com.niicz.sunshinekotlin.forecast

import com.niicz.sunshinekotlin.di.ActivityScoped
import com.niicz.sunshinekotlin.di.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector


/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * [ForecastPresenter].
 */
@Module
abstract class ForecastModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun forecastFragment(): ForecastFragment

    @ActivityScoped
    @Binds
    abstract fun forecastPresenter(presenter: ForecastPresenter): ForecastContract.Presenter
}