package com.niicz.sunshinekotlin.data.repository

import com.niicz.sunshinekotlin.data.room.LocationEntry
import com.niicz.sunshinekotlin.data.room.WeatherEntry
import io.reactivex.Flowable


interface WeatherDataSource {

    fun getWeatherEntries(location: String, forceRemote: Boolean): Flowable<MutableList<WeatherEntry>>

    fun saveWeatherEntries(weatherEntries: List<WeatherEntry>)

    fun getWeatherEntryById(wID: Long): Flowable<WeatherEntry>

    fun deleteAllWeatherEntries()

    fun insertWeatherEntry(weatherEntry: WeatherEntry):Long
    fun getLocationEntry(location: String, forceRemote: Boolean): Flowable<LocationEntry>
}