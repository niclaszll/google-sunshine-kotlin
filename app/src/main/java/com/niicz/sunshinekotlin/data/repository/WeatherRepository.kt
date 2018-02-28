package com.niicz.sunshinekotlin.data.repository

import com.niicz.sunshinekotlin.data.repository.local.WeatherContract
import io.reactivex.Flowable
import javax.inject.Inject


class WeatherRepository @Inject constructor(@Local private var localDataSource: WeatherDataSource, @Remote private var remoteDataSource: WeatherDataSource) :
    WeatherDataSource {

    override fun getWeatherEntries(forceRemote: Boolean): Flowable<List<WeatherContract.WeatherEntry>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveWeatherEntries(weatherEntries: List<WeatherContract.WeatherEntry>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllWeatherEntries() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}