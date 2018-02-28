package com.niicz.sunshinekotlin.data.source.local

import android.arch.persistence.room.*
import com.niicz.sunshinekotlin.data.WeatherContract

@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    fun getAll(): List<WeatherContract.LocationEntry>

    @Query("SELECT * FROM location WHERE city=:cityName")
    fun getByCityName(cityName: String): WeatherContract.LocationEntry

    @Insert
    fun insert(locationEntry: WeatherContract.LocationEntry)

    @Insert
    fun insertAll(locationEntries: List<WeatherContract.LocationEntry>)

    @Update
    fun update(locationEntries: WeatherContract.LocationEntry)

    @Delete
    fun delete(locationEntry: WeatherContract.LocationEntry)
}