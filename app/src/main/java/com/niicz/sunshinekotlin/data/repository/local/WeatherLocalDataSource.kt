package com.niicz.sunshinekotlin.data.repository.local

import com.niicz.sunshinekotlin.data.repository.WeatherDataSource
import com.niicz.sunshinekotlin.data.room.WeatherContract
import com.niicz.sunshinekotlin.data.room.WeatherDao
import io.reactivex.Flowable
import javax.inject.Inject


class WeatherLocalDataSource @Inject constructor(private var weatherDao: WeatherDao): WeatherDataSource {

    override fun insertWeatherEntry(weatherEntry: WeatherContract.WeatherEntry) {
        return weatherDao.insert(weatherEntry)
    }

    override fun getWeatherEntries(forceRemote: Boolean): Flowable<MutableList<WeatherContract.WeatherEntry>> {
        return weatherDao.getAll()
    }

    override fun saveWeatherEntries(weatherEntries: List<WeatherContract.WeatherEntry>) {
        return weatherDao.insertAll(weatherEntries)
    }

    override fun deleteAllWeatherEntries() {
        weatherDao.clearWeatherTable()
    }
}