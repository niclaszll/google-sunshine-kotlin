package com.niicz.sunshinekotlin.data

import android.arch.persistence.room.*

@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    fun getAll(): List<WeatherContract.LocationEntry>

    @Insert
    fun insertAll(locationEntries: List<WeatherContract.LocationEntry>)

    @Update
    fun update(locationEntries: WeatherContract.LocationEntry)

    @Delete
    fun delete(locationEntry: WeatherContract.LocationEntry)
}