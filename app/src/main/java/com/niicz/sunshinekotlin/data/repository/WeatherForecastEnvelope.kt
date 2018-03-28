package com.niicz.sunshinekotlin.data.repository

import com.google.gson.annotations.SerializedName

class WeatherForecastEnvelope {

    @SerializedName("list")
    var forecast: MutableList<ForecastData> = mutableListOf()

    @SerializedName("city")
    var city: Location? = null

    class ForecastData {

        @SerializedName("dt")
        var date: String = ""

        @SerializedName("main")
        var main: Main? = null

        @SerializedName("weather")
        var weather: ArrayList<Weather>? = null

        @SerializedName("wind")
        var wind: Wind? = null


        class Wind {
            @SerializedName("speed")
            var wind: Double = 0.0

            @SerializedName("deg")
            var degrees: String = ""
        }

        class Weather {
            @SerializedName("id")
            var weatherID: String = ""

            @SerializedName("description")
            var description: String = ""
        }

        class Main {
            @SerializedName("temp_min")
            var min: Double = 0.0

            @SerializedName("temp_max")
            var max: Double = 0.0

            @SerializedName("humidity")
            var humidity: Double = 0.0

            @SerializedName("pressure")
            var pressure: Double = 0.0
        }

    }

    class Location {

        @SerializedName("id")
        var lID: Long = 0

        @SerializedName("name")
        var cityName: String = ""

        @SerializedName("country")
        var country: String = ""

        @SerializedName("coord")
        var coord: Coord? = null

        class Coord {
            @SerializedName("lat")
            var coordLat: String = ""

            @SerializedName("lon")
            var coordLong: String = ""

        }
    }

}