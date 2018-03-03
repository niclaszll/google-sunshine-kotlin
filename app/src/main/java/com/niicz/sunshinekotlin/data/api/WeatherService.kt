package com.niicz.sunshinekotlin.data.api

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/forecast")
    fun getForecast(@Query("q") location: String,
                    @Query("mode") format: String,
                    @Query("units") units: String,
                    @Query("appid") appID: String): Flowable<WeatherForecastResponse>
}