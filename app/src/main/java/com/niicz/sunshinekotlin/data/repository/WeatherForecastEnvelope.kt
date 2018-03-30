package com.niicz.sunshinekotlin.data.repository

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class WeatherForecastEnvelope {

    @SerializedName("list")
    var forecast: MutableList<ForecastData> = mutableListOf()

    @SerializedName("city")
    var city: Location? = null

    class ForecastData() : Parcelable {

        @SerializedName("dt")
        var date: String = ""

        @SerializedName("main")
        var main: Main? = null

        @SerializedName("weather")
        var weather: ArrayList<Weather>? = null

        @SerializedName("wind")
        var wind: Wind? = null

        constructor(parcel: Parcel) : this() {
            date = parcel.readString()
            main = parcel.readParcelable(Main::class.java.classLoader)
            wind = parcel.readParcelable(Wind::class.java.classLoader)
        }


        class Wind() : Parcelable {
            @SerializedName("speed")
            var wind: Double = 0.0

            @SerializedName("deg")
            var degrees: String = ""

            constructor(parcel: Parcel) : this() {
                wind = parcel.readDouble()
                degrees = parcel.readString()
            }

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeDouble(wind)
                parcel.writeString(degrees)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<Wind> {
                override fun createFromParcel(parcel: Parcel): Wind {
                    return Wind(parcel)
                }

                override fun newArray(size: Int): Array<Wind?> {
                    return arrayOfNulls(size)
                }
            }
        }

        class Weather() : Parcelable {
            @SerializedName("id")
            var weatherID: String = ""

            @SerializedName("description")
            var description: String = ""

            constructor(parcel: Parcel) : this() {
                weatherID = parcel.readString()
                description = parcel.readString()
            }

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(weatherID)
                parcel.writeString(description)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<Weather> {
                override fun createFromParcel(parcel: Parcel): Weather {
                    return Weather(parcel)
                }

                override fun newArray(size: Int): Array<Weather?> {
                    return arrayOfNulls(size)
                }
            }
        }

        class Main() : Parcelable {
            @SerializedName("temp_min")
            var min: Double = 0.0

            @SerializedName("temp_max")
            var max: Double = 0.0

            @SerializedName("humidity")
            var humidity: Double = 0.0

            @SerializedName("pressure")
            var pressure: Double = 0.0

            constructor(parcel: Parcel) : this() {
                min = parcel.readDouble()
                max = parcel.readDouble()
                humidity = parcel.readDouble()
                pressure = parcel.readDouble()
            }

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeDouble(min)
                parcel.writeDouble(max)
                parcel.writeDouble(humidity)
                parcel.writeDouble(pressure)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<Main> {
                override fun createFromParcel(parcel: Parcel): Main {
                    return Main(parcel)
                }

                override fun newArray(size: Int): Array<Main?> {
                    return arrayOfNulls(size)
                }
            }
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(date)
            parcel.writeParcelable(main, flags)
            parcel.writeParcelable(wind, flags)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<ForecastData> {
            override fun createFromParcel(parcel: Parcel): ForecastData {
                return ForecastData(parcel)
            }

            override fun newArray(size: Int): Array<ForecastData?> {
                return arrayOfNulls(size)
            }
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