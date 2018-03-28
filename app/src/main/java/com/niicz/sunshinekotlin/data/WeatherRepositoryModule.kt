package com.niicz.sunshinekotlin.data

import com.niicz.sunshinekotlin.data.repository.WeatherDataSource
import com.niicz.sunshinekotlin.data.repository.remote.WeatherRemoteDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class WeatherRepositoryModule {

    @Singleton
    @Binds
    abstract fun provideRemoteDataSource(weatherRemoteDataSource: WeatherRemoteDataSource):WeatherDataSource
}