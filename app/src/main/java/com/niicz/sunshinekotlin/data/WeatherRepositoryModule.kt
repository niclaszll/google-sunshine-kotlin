package com.niicz.sunshinekotlin.data

import com.niicz.sunshinekotlin.data.repository.Local
import com.niicz.sunshinekotlin.data.repository.Remote
import com.niicz.sunshinekotlin.data.repository.WeatherDataSource
import com.niicz.sunshinekotlin.data.repository.local.WeatherLocalDataSource
import com.niicz.sunshinekotlin.data.repository.remote.WeatherRemoteDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class WeatherRepositoryModule {

    @Singleton
    @Binds
    @Local
    abstract fun provideLocalDataSource(weatherLocalDataSource: WeatherLocalDataSource):WeatherDataSource

    @Singleton
    @Binds
    @Remote
    abstract fun provideRemoteDataSource(weatherRemoteDataSource: WeatherRemoteDataSource):WeatherDataSource
}