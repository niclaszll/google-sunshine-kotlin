package com.niicz.sunshinekotlin.data.repository.remote

import com.niicz.sunshinekotlin.data.room.WeatherContract
import com.niicz.sunshinekotlin.data.repository.WeatherDataSource
import com.niicz.sunshinekotlin.network.FetchWeatherTask
import io.reactivex.Flowable
import javax.inject.Inject


class WeatherRemoteDataSource @Inject constructor(private var fetchWeatherTask: FetchWeatherTask): WeatherDataSource{

    override fun getWeatherEntries(forceRemote: Boolean): Flowable<List<WeatherContract.WeatherEntry>> {
        throw UnsupportedOperationException("Unsupported operation")
    }

    override fun saveWeatherEntries(weatherEntries: List<WeatherContract.WeatherEntry>) {
        throw UnsupportedOperationException("Unsupported operation")
    }

    override fun deleteAllWeatherEntries() {
        throw UnsupportedOperationException("Unsupported operation")
    }
}