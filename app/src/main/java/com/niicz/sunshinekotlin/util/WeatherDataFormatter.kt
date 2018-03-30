package com.niicz.sunshinekotlin.util

import android.content.res.Resources
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


/**
 * Quick and dirty day formatter helper class.
 */
class WeatherDataFormatter {

    private val locale = Resources.getSystem().configuration.locale!!

    /**
     * Format a Unix timestamp into a human readable week day String such as "Today", "Tomorrow"
     * and "Wednesday"
     */
    fun format(unixTimestamp: Long): String {
        val milliseconds = unixTimestamp * 1000
        val day: String

        day = when {
            isToday(milliseconds) -> "Today"
            isTomorrow(milliseconds) -> "Tomorrow"
            else -> getDayOfWeek(milliseconds)
        }

        return day
    }

    private fun getDayOfWeek(milliseconds: Long): String {

        return SimpleDateFormat("EEEE", locale).format(Date(milliseconds))
    }

    private fun isToday(milliseconds: Long): Boolean {
        val dayInYearFormat = SimpleDateFormat("yyyyD", locale)
        val nowHash = dayInYearFormat.format(Date())
        val comparisonHash = dayInYearFormat.format(Date(milliseconds))
        return nowHash == comparisonHash
    }

    private fun isTomorrow(milliseconds: Long): Boolean {
        val dayInYearFormat = SimpleDateFormat("yyyyD", locale)
        val tomorrowHash = Integer.parseInt(dayInYearFormat.format(Date())) + 1
        val comparisonHash = Integer.parseInt(dayInYearFormat.format(Date(milliseconds)))
        return comparisonHash == tomorrowHash
    }

    fun formatTemp(unitType: String, temp: Double): Double {

        var newTemp: Double = temp

        if (unitType == "imperial") {
            newTemp = (temp * 1.8) + 32
        } else if (unitType != "metric") {
            Log.e("FormatWeatherData", "UnitType not found!")
        }

        return Math.round(newTemp).toDouble()
    }
}