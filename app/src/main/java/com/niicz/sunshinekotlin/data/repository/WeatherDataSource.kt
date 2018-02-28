package com.niicz.sunshinekotlin.data.repository

import com.niicz.sunshinekotlin.data.repository.local.WeatherContract
import io.reactivex.Flowable


interface WeatherDataSource {

    fun getWeatherEntries(forceRemote: Boolean): Flowable<List<WeatherContract.WeatherEntry>>

    fun saveWeatherEntries(weatherEntries: List<WeatherContract.WeatherEntry>)

    fun deleteAllWeatherEntries()
}