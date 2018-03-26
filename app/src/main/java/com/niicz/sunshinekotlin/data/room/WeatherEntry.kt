package com.niicz.sunshinekotlin.data.room

import android.arch.persistence.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(
    tableName = WeatherEntry.TABLE_NAME,
    foreignKeys = [(ForeignKey(
        entity = LocationEntry::class,
        parentColumns = [("lKey")],
        childColumns = ["locationKey"],
        onDelete = ForeignKey.CASCADE
    ))]
)
data class WeatherEntry(
    @SerializedName("dt")
    @ColumnInfo(name = COLUMN_DATE)
    var date: String = "",

    @ColumnInfo(name = COLUMN_LOC_KEY)
    var locationKey: Long? = null,

    @SerializedName("main")
    @Embedded(prefix = "main_")
    var main: Main? = null,

    @SerializedName("weather")
    @TypeConverters(Converters::class)
    @Embedded(prefix = "weather_")
    var weather: ArrayList<Weather>? = null,

    @SerializedName("wind")
    @Embedded(prefix = "wind_")
    var wind: Wind? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0


    class Wind {
        @SerializedName("speed")
        @ColumnInfo(name = COLUMN_WIND_SPEED)
        var wind: Double = 0.0

        @SerializedName("deg")
        @ColumnInfo(name = COLUMN_DEGREES)
        var degrees: String = ""
    }

    class Weather {
        @SerializedName("id")
        @ColumnInfo(name = COLUMN_WEATHER_ID)
        var weatherID: String = ""

        @SerializedName("description")
        @ColumnInfo(name = COLUMN_SHORT_DESC)
        var description: String = ""
    }

    class Main {
        @SerializedName("temp_min")
        @ColumnInfo(name = COLUMN_MIN_TEMP)
        var min: Double = 0.0

        @SerializedName("temp_max")
        @ColumnInfo(name = COLUMN_MAX_TEMP)
        var max: Double = 0.0

        @SerializedName("humidity")
        @ColumnInfo(name = COLUMN_HUMIDITY)
        var humidity: Double = 0.0

        @SerializedName("pressure")
        @ColumnInfo(name = COLUMN_PRESSURE)
        var pressure: Double = 0.0
    }

    companion object {

        const val TABLE_NAME = "weather"
        const val COLUMN_DATE = "date"
        const val COLUMN_WEATHER_ID = "weatherID"
        const val COLUMN_LOC_KEY = "locationKey"
        const val COLUMN_SHORT_DESC = "description"
        const val COLUMN_MIN_TEMP = "min"
        const val COLUMN_MAX_TEMP = "max"
        const val COLUMN_HUMIDITY = "humidity"
        const val COLUMN_PRESSURE = "pressure"
        const val COLUMN_WIND_SPEED = "wind"
        const val COLUMN_DEGREES = "degrees"
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