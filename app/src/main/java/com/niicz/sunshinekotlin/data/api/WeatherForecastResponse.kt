package com.niicz.sunshinekotlin.data.api

import com.google.gson.annotations.SerializedName


data class WeatherForecastResponse(
    @SerializedName("test") val test: String,
    @SerializedName("test2") val test2: String
)