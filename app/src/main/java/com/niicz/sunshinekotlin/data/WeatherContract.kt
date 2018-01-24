package com.niicz.sunshinekotlin.data

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
        @PrimaryKey(autoGenerate = true)
        val locationID: Long,
        val locationSetting: String,
        val city: String,
        val coordLat: String,
        val coordLong: String
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
    data class WeatherEntry (
        @PrimaryKey(autoGenerate = true)
        val weatherID: Long,
        val locationKey: Long,
        val date: String,
        val description: String,
        val min: Double,
        val max: Double,
        val humidity: Double,
        val pressure: Double,
        val wind: Double,
        val degrees: String
    )

}