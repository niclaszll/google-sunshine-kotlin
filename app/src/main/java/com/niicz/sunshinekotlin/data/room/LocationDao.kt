package com.niicz.sunshinekotlin.data.room

import android.arch.persistence.room.*
import io.reactivex.Flowable


@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    fun getAll(): List<LocationEntry>

    @Query("SELECT * FROM location WHERE cityName=:cityName")
    fun getByCityName(cityName: String): Flowable<LocationEntry>

    @Insert
    fun insert(locationEntry: LocationEntry)

    @Insert
    fun insertAll(locationEntries: List<LocationEntry>)

    @Update
    fun update(locationEntries: LocationEntry)

    @Delete
    fun delete(locationEntry: LocationEntry)
}