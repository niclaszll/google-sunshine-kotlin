package com.niicz.sunshinekotlin.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.text.format.Time


class WeatherContract {

    fun normalizeDate(startDate: Long): Long {
        // normalize the start date to the beginning of the (UTC) day
        val time = Time()
        time.set(startDate)
        val julianDay = Time.getJulianDay(startDate, time.gmtoff)
        return time.setJulianDay(julianDay)
    }


    @Entity(tableName = "location")
    data class LocationEntry constructor(
        @PrimaryKey(autoGenerate = true) val locationID: String,
        @ColumnInfo(name = "location_setting") val locationSetting: String,
        @ColumnInfo(name = "city_name") val city: String,
        @ColumnInfo(name = "coord_lat") val coordLat: String,
        @ColumnInfo(name = "coord_long") val coordLong: String
    )

    @Entity(
        tableName = "weather",
        foreignKeys = [(ForeignKey(
            entity = LocationEntry::class,
            parentColumns = [("locationID")],
            childColumns = ["locationKey"],
            onDelete = ForeignKey.CASCADE
        ))]
    )
    data class WeatherEntry constructor(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "location_key") val locationKey: String,
        @ColumnInfo(name = "date") val date: String,
        @ColumnInfo(name = "description") val description: String,
        @ColumnInfo(name = "min_temp") val min: Float,
        @ColumnInfo(name = "max_temp") val max: Float,
        @ColumnInfo(name = "humidity") val humidity: Float,
        @ColumnInfo(name = "pressure") val pressure: Float,
        @ColumnInfo(name = "wind") val wind: Float,
        @ColumnInfo(name = "degrees") val degrees: String
    )


}