package com.niicz.sunshinekotlin.data.repository

import io.reactivex.Flowable
import javax.inject.Inject


class WeatherRepository @Inject constructor(private var remoteDataSource: WeatherDataSource) :
    WeatherDataSource {

    override fun getWeatherForecast(location: String): Flowable<WeatherForecastEnvelope> {

        return remoteDataSource.getWeatherForecast(location)

    }
}