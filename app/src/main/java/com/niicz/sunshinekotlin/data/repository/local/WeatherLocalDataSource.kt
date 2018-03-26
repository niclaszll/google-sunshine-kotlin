package com.niicz.sunshinekotlin.data.repository.local

import com.niicz.sunshinekotlin.data.repository.WeatherDataSource
import com.niicz.sunshinekotlin.data.room.LocationDao
import com.niicz.sunshinekotlin.data.room.LocationEntry
import com.niicz.sunshinekotlin.data.room.WeatherEntry
import com.niicz.sunshinekotlin.data.room.WeatherDao
import io.reactivex.Flowable
import javax.inject.Inject


class WeatherLocalDataSource @Inject constructor(private var weatherDao: WeatherDao, private var locationDao: LocationDao): WeatherDataSource {

    override fun insertWeatherEntry(weatherEntry: WeatherEntry):Long {
        return weatherDao.insert(weatherEntry)
    }

    override fun getWeatherEntries(location: String, forceRemote: Boolean): Flowable<MutableList<WeatherEntry>> {
        //TODO change to location specific data not all!
        return weatherDao.getAll()
    }

    override fun getLocationEntry(location: String, forceRemote: Boolean): Flowable<LocationEntry> {
        return locationDao.getByCityName(location)
    }

    override fun getWeatherEntryById(wID: Long) : Flowable<WeatherEntry> {
        return weatherDao.getWeatherById(wID)
    }

    override fun saveWeatherEntries(weatherEntries: List<WeatherEntry>) {
        return weatherDao.insertAll(weatherEntries)
    }

    override fun deleteAllWeatherEntries() {
        weatherDao.clearWeatherTable()
    }
}