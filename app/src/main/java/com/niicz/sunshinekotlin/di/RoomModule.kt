package com.niicz.sunshinekotlin.di

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.niicz.sunshinekotlin.data.LocationDao
import com.niicz.sunshinekotlin.data.WeatherDao
import com.niicz.sunshinekotlin.data.WeatherDatabase
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @Provides
    fun providesWeatherDatabase(context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather-db"
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun providesWeatherDao(database: WeatherDatabase): WeatherDao{
        return database.weatherDao()
    }

    @Provides
    fun providesLocationDao(database: WeatherDatabase): LocationDao{
        return database.locationDao()
    }
}