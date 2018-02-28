package com.niicz.sunshinekotlin.data.repository.local

import android.arch.persistence.room.*

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