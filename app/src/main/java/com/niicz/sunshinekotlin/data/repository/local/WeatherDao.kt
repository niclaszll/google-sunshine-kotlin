package com.niicz.sunshinekotlin.data.repository.local

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather")
    fun getAll(): Flowable<List<WeatherContract.WeatherEntry>>

    @Insert
    fun insertAll(weatherEntries: List<WeatherContract.WeatherEntry>)

    @Insert
    fun insert(weatherEntry: WeatherContract.WeatherEntry)

    @Update
    fun update(weatherEntry: WeatherContract.WeatherEntry)

    @Query("DELETE FROM weather")
    fun clearWeatherTable()

    @Delete
    fun delete(weatherEntry: WeatherContract.WeatherEntry)

    @Query("SELECT * FROM weather WHERE locationKey=:locationID")
    fun findWeatherForLocation(locationID: Long): List<WeatherContract.WeatherEntry>
}