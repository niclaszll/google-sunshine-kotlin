package com.niicz.sunshinekotlin.data.room

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather")
    fun getAll(): Flowable<MutableList<WeatherContract.WeatherEntry>>

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
}