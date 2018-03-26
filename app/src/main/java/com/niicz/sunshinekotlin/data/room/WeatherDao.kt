package com.niicz.sunshinekotlin.data.room

import android.arch.persistence.room.*
import com.niicz.sunshinekotlin.data.room.WeatherEntry.Companion.TABLE_NAME
import io.reactivex.Flowable

@Dao
interface WeatherDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): Flowable<MutableList<WeatherEntry>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id == :id")
    fun getWeatherById(id: Long): Flowable<WeatherEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weatherEntries: List<WeatherEntry>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntry: WeatherEntry): Long

    @Update
    fun update(weatherEntry: WeatherEntry)

    @Query("DELETE FROM $TABLE_NAME")
    fun clearWeatherTable()

    @Delete
    fun delete(weatherEntry: WeatherEntry)
}