package com.niicz.sunshinekotlin.data.source.remote

import com.niicz.sunshinekotlin.data.WeatherContract
import com.niicz.sunshinekotlin.data.source.WeatherDataSource
import com.niicz.sunshinekotlin.network.FetchWeatherTask
import io.reactivex.Flowable
import javax.inject.Inject


class WeatherRemoteDataSource @Inject constructor(private var fetchWeatherTask: FetchWeatherTask): WeatherDataSource{

    override fun getWeatherEntries(): Flowable<List<WeatherContract.WeatherEntry>> {
        throw UnsupportedOperationException("Unsupported operation")
    }

    override fun saveWeatherEntries(weatherEntries: List<WeatherContract.WeatherEntry>) {
        throw UnsupportedOperationException("Unsupported operation")
    }

    override fun deleteAllWeatherEntries() {
        throw UnsupportedOperationException("Unsupported operation")
    }
}