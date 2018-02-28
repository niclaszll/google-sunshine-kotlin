package com.niicz.sunshinekotlin.data.repository.local

import com.niicz.sunshinekotlin.data.repository.WeatherDataSource
import io.reactivex.Flowable
import javax.inject.Inject


class WeatherLocalDataSource @Inject constructor(private var weatherDao: WeatherDao): WeatherDataSource {

    override fun getWeatherEntries(forceRemote: Boolean): Flowable<List<WeatherContract.WeatherEntry>> {
        return weatherDao.getAll()
    }

    override fun saveWeatherEntries(weatherEntries: List<WeatherContract.WeatherEntry>) {
        return weatherDao.insertAll(weatherEntries)
    }

    override fun deleteAllWeatherEntries() {
        weatherDao.clearWeatherTable()
    }
}