package com.niicz.sunshinekotlin.data.source.local

import com.niicz.sunshinekotlin.data.WeatherContract
import com.niicz.sunshinekotlin.data.source.WeatherDataSource
import io.reactivex.Flowable
import javax.inject.Inject


class WeatherLocalDataSource @Inject constructor(private var weatherDao: WeatherDao): WeatherDataSource {

    override fun getWeatherEntries(): Flowable<List<WeatherContract.WeatherEntry>> {
        return weatherDao.getAll()
    }

    override fun saveWeatherEntries(weatherEntries: List<WeatherContract.WeatherEntry>) {
        return weatherDao.insertAll(weatherEntries)
    }

    override fun deleteAllWeatherEntries() {
        weatherDao.clearWeatherTable()
    }
}