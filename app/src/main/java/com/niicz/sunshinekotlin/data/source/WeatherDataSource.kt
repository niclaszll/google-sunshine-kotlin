package com.niicz.sunshinekotlin.data.source

import com.niicz.sunshinekotlin.data.WeatherContract
import io.reactivex.Flowable


interface WeatherDataSource {

    fun getWeatherEntries(): Flowable<List<WeatherContract.WeatherEntry>>

    fun saveWeatherEntries(weatherEntries: List<WeatherContract.WeatherEntry>)

    fun deleteAllWeatherEntries()
}