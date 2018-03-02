package com.niicz.sunshinekotlin.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/forecast")
    fun getForecast(@Query("q") location: String,
                    @Query("mode") format: String,
                    @Query("units") units: String,
                    @Query("appid") appID: String)
}