package com.niicz.sunshinekotlin.detail

import com.niicz.sunshinekotlin.di.ActivityScoped
import com.niicz.sunshinekotlin.di.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * [DetailPresenter].
 */
@Module
abstract class DetailModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun detailFragment(): DetailFragment

    @ActivityScoped
    @Binds
    abstract fun detailPresenter(presenter: DetailPresenter): DetailContract.Presenter
}