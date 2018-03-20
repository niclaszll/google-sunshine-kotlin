package com.niicz.sunshinekotlin.data.api

import com.google.gson.annotations.SerializedName
import com.niicz.sunshinekotlin.data.room.WeatherContract

class WeatherForecastResponse {

    @SerializedName("list")
    var weatherEntries: MutableList<WeatherContract.WeatherEntry>? = null

}