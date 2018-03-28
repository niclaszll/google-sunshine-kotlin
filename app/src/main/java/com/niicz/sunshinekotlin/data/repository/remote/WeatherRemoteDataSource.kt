package com.niicz.sunshinekotlin.data.repository.remote

import com.niicz.sunshinekotlin.BuildConfig
import com.niicz.sunshinekotlin.data.api.WeatherService
import com.niicz.sunshinekotlin.data.repository.WeatherDataSource
import com.niicz.sunshinekotlin.data.repository.WeatherForecastEnvelope
import io.reactivex.Flowable
import javax.inject.Inject


class WeatherRemoteDataSource @Inject constructor(private var weatherService: WeatherService) :
    WeatherDataSource {

    override fun getWeatherForecast(location: String): Flowable<WeatherForecastEnvelope> {
        return weatherService.getForecast(
            location,
            "json",
            "metric",
            BuildConfig.OPEN_WEATHER_MAP_API_KEY
        )
    }
}