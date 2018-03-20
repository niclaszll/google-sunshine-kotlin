package com.niicz.sunshinekotlin.data.repository

import com.niicz.sunshinekotlin.data.room.WeatherContract
import io.reactivex.Flowable


interface WeatherDataSource {

    fun getWeatherEntries(location: String, forceRemote: Boolean): Flowable<MutableList<WeatherContract.WeatherEntry>>

    fun saveWeatherEntries(weatherEntries: List<WeatherContract.WeatherEntry>)

    fun deleteAllWeatherEntries()

    fun insertWeatherEntry(weatherEntry: WeatherContract.WeatherEntry)
}