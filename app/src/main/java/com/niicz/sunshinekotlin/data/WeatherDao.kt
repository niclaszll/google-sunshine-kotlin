package com.niicz.sunshinekotlin.data

import android.arch.persistence.room.*


@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherEntry")
    fun getAll():List<WeatherContract.WeatherEntry>

    @Insert
    fun insertAll(vararg weatherEntries: WeatherContract.WeatherEntry)

    @Update
    fun updateUsers(vararg weatherEntries: WeatherContract.WeatherEntry)

    @Delete
    fun delete(weatherEntry: WeatherContract.WeatherEntry)
}