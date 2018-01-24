package com.niicz.sunshinekotlin.data

import android.arch.persistence.room.*


@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather")
    fun getAll():List<WeatherContract.WeatherEntry>

    @Insert
    fun insertAll(weatherEntries: List<WeatherContract.WeatherEntry>)

    @Update
    fun update(weatherEntries: WeatherContract.WeatherEntry)

    @Query("DELETE FROM weather")
    fun clearWeatherTable()

    @Delete
    fun delete(weatherEntry: WeatherContract.WeatherEntry)

    @Query("SELECT * FROM weather WHERE locationKey=:locationID")
    fun findWeatherForLocation(locationID: Long): List<WeatherContract.WeatherEntry>
}