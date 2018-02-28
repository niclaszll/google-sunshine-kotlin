package com.niicz.sunshinekotlin.data.source.local

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @Provides
    fun providesWeatherDatabase(context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather"
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun providesWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao()
    }

    @Provides
    fun providesLocationDao(database: WeatherDatabase): LocationDao {
        return database.locationDao()
    }
}
