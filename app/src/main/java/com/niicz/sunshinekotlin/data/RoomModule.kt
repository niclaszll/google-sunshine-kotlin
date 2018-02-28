package com.niicz.sunshinekotlin.data

import android.arch.persistence.room.Room
import android.content.Context
import com.niicz.sunshinekotlin.data.repository.local.LocationDao
import com.niicz.sunshinekotlin.data.repository.local.WeatherDao
import com.niicz.sunshinekotlin.data.repository.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun providesWeatherDatabase(context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather"
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun providesWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao()
    }

    @Singleton
    @Provides
    fun providesLocationDao(database: WeatherDatabase): LocationDao {
        return database.locationDao()
    }
}
