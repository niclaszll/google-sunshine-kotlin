package com.niicz.sunshinekotlin.data.repository.remote

import com.niicz.sunshinekotlin.BuildConfig
import com.niicz.sunshinekotlin.data.api.WeatherForecastResponse
import com.niicz.sunshinekotlin.data.api.WeatherService
import com.niicz.sunshinekotlin.data.repository.WeatherDataSource
import com.niicz.sunshinekotlin.data.room.WeatherEntry
import io.reactivex.Flowable
import javax.inject.Inject


class WeatherRemoteDataSource @Inject constructor(private var weatherService: WeatherService) :
    WeatherDataSource {

    override fun getWeatherEntries(location: String, forceRemote: Boolean): Flowable<MutableList<WeatherEntry>> {
        return weatherService.getForecast(
            location,
            "json",
            "metric",
            BuildConfig.OPEN_WEATHER_MAP_API_KEY
        ).map(WeatherForecastResponse::weatherEntries)
    }

    override fun getWeatherEntryById(wID: Long): Flowable<WeatherEntry> {
        throw UnsupportedOperationException("Unsupported operation")
    }

    override fun saveWeatherEntries(weatherEntries: List<WeatherEntry>) {
        throw UnsupportedOperationException("Unsupported operation")
    }

    override fun insertWeatherEntry(weatherEntry: WeatherEntry): Long {
        throw UnsupportedOperationException("Unsupported operation")
    }

    override fun deleteAllWeatherEntries() {
        throw UnsupportedOperationException("Unsupported operation")
    }
}