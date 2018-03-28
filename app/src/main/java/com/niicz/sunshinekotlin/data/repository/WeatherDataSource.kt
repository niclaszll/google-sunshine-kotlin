package com.niicz.sunshinekotlin.data.repository

import io.reactivex.Flowable


interface WeatherDataSource {

    fun getWeatherForecast(location: String): Flowable<WeatherForecastEnvelope>

}