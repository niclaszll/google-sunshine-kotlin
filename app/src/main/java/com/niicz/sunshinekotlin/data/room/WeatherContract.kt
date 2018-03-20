package com.niicz.sunshinekotlin.data.room

import android.arch.persistence.room.*
import android.provider.BaseColumns
import com.google.gson.annotations.SerializedName
import java.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.collections.ArrayList


class WeatherContract {

    @Entity(tableName = LocationEntry.TABLE_NAME)
    class LocationEntry {

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(index = true, name = COLUMN_lID)
        var lID: Long = 0

        @ColumnInfo(name = COLUMN_LOCATION_SETTING)
        var locationSetting: String = ""

        @ColumnInfo(name = COLUMN_CITY_NAME)
        var city: String = ""

        @ColumnInfo(name = COLUMN_COORD_LAT)
        var coordLat: String = ""

        @ColumnInfo(name = COLUMN_COORD_LONG)
        var coordLong: String = ""

        companion object {
            const val TABLE_NAME = "location"
            const val COLUMN_lID = "lID"
            const val COLUMN_LOCATION_SETTING = "locationSetting"
            const val COLUMN_CITY_NAME = "city"
            const val COLUMN_COORD_LAT = "coordLat"
            const val COLUMN_COORD_LONG = "coordLong"
        }
    }


    //location entry removed for simplicity
    @Entity(tableName = WeatherEntry.TABLE_NAME)
    class WeatherEntry {

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(index = true, name = COLUMN_wID)
        var wID: Long = 0

        @SerializedName("dt")
        @ColumnInfo(name = COLUMN_DATE)
        var date: String = ""

        @SerializedName("main")
        @Embedded(prefix = "main_")
        var main: Main? = null

        @SerializedName("weather")
        @TypeConverters(Converters::class)
        @Embedded(prefix = "weather_")
        var weather: ArrayList<Weather>? = null

        @SerializedName("wind")
        @Embedded(prefix = "wind_")
        var wind: Wind? = null


        companion object {

            const val TABLE_NAME = "weather"
            const val COLUMN_wID = BaseColumns._ID
            const val COLUMN_DATE = "date"
            const val COLUMN_WEATHER_ID = "weatherID"
            const val COLUMN_SHORT_DESC = "description"
            const val COLUMN_MIN_TEMP = "min"
            const val COLUMN_MAX_TEMP = "max"
            const val COLUMN_HUMIDITY = "humidity"
            const val COLUMN_PRESSURE = "pressure"
            const val COLUMN_WIND_SPEED = "wind"
            const val COLUMN_DEGREES = "degrees"

        }

    }

    class Wind {
        @SerializedName("speed")
        @ColumnInfo(name = WeatherEntry.COLUMN_WIND_SPEED)
        var wind: Double = 0.0

        @SerializedName("deg")
        @ColumnInfo(name = WeatherEntry.COLUMN_DEGREES)
        var degrees: String = ""
    }

    class Weather {
        @SerializedName("id")
        @ColumnInfo(name = WeatherEntry.COLUMN_WEATHER_ID)
        var weatherID: String = ""

        @SerializedName("description")
        @ColumnInfo(name = WeatherEntry.COLUMN_SHORT_DESC)
        var description: String = ""
    }

    class Main {
        @SerializedName("temp_min")
        @ColumnInfo(name = WeatherEntry.COLUMN_MIN_TEMP)
        var min: Double = 0.0

        @SerializedName("temp_max")
        @ColumnInfo(name = WeatherEntry.COLUMN_MAX_TEMP)
        var max: Double = 0.0

        @SerializedName("humidity")
        @ColumnInfo(name = WeatherEntry.COLUMN_HUMIDITY)
        var humidity: Double = 0.0

        @SerializedName("pressure")
        @ColumnInfo(name = WeatherEntry.COLUMN_PRESSURE)
        var pressure: Double = 0.0
    }

    /* convert list to string and back, because room cannot store list of objects*/
    object Converters {
        @TypeConverter
        fun fromString(value: String): ArrayList<String> {
            val listType = object : TypeToken<ArrayList<String>>() {

            }.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        fun fromArrayList(list: ArrayList<String>): String {
            val gson = Gson()
            return gson.toJson(list)
        }
    }
}