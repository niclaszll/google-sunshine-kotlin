package com.niicz.sunshinekotlin.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.niicz.sunshinekotlin.data.WeatherContract


@Database(
    entities = [(WeatherContract.WeatherEntry::class), (WeatherContract.LocationEntry::class)],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
    abstract fun locationDao(): LocationDao
}