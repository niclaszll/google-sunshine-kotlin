package com.niicz.sunshinekotlin.data.repository.remote

import android.util.Log
import com.niicz.sunshinekotlin.BuildConfig
import com.niicz.sunshinekotlin.data.api.WeatherForecastResponse
import com.niicz.sunshinekotlin.data.api.WeatherService
import com.niicz.sunshinekotlin.data.repository.WeatherDataSource
import com.niicz.sunshinekotlin.data.room.WeatherContract
import io.reactivex.Flowable
import javax.inject.Inject


class WeatherRemoteDataSource @Inject constructor(var weatherService: WeatherService) :
    WeatherDataSource {


    override fun getWeatherEntries(forceRemote: Boolean): Flowable<MutableList<WeatherContract.WeatherEntry>> {
        //TODO needs to be dynamic
        Log.v("RemoteDataS", "Test")
        return weatherService.getForecast(
            "Dresden",
            "json",
            "metric",
            BuildConfig.OPEN_WEATHER_MAP_API_KEY
        ).map(WeatherForecastResponse::weatherEntries)
    }

    override fun saveWeatherEntries(weatherEntries: List<WeatherContract.WeatherEntry>) {
        throw UnsupportedOperationException("Unsupported operation")
    }

    override fun insertWeatherEntry(weatherEntry: WeatherContract.WeatherEntry) {
        throw UnsupportedOperationException("Unsupported operation")
    }

    override fun deleteAllWeatherEntries() {
        throw UnsupportedOperationException("Unsupported operation")
    }
}