package com.niicz.sunshinekotlin.data.room

import android.arch.persistence.room.Room
import android.content.Context
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
