package com.niicz.sunshinekotlin.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(
    entities = [(WeatherContract.WeatherEntry::class), (WeatherContract.LocationEntry::class)],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
    abstract fun locationDao(): LocationDao
}