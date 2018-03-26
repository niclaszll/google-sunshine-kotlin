package com.niicz.sunshinekotlin.data

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.niicz.sunshinekotlin.data.room.WeatherDatabase
import com.niicz.sunshinekotlin.data.room.WeatherEntry
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class DatabaseTest {

    private lateinit var weatherDatabase: WeatherDatabase
    private val weatherEntry: WeatherEntry =
        WeatherEntry()
    @Before
    fun initDb() {
        weatherDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            WeatherDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        weatherDatabase.close()
    }
    /*
    @Test
    fun insertAllSavesData() {

        weatherDatabase.locationDao().insertAll(listOf(locationEntry))
        weatherDatabase.weatherDao().insertAll(listOf(weatherEntry))
        assertEquals(weatherDatabase.weatherDao().getAll()[0], weatherEntry)
    }

    @Test
    fun clearWeatherTableClearsAll() {

        weatherDatabase.locationDao().insertAll(listOf(locationEntry))
        weatherDatabase.weatherDao().insertAll(listOf(weatherEntry))

        assertTrue(weatherDatabase.weatherDao().getAll().isNotEmpty())

        weatherDatabase.weatherDao().clearWeatherTable()

        assertTrue(weatherDatabase.weatherDao().getAll().isEmpty())

    }
    */
}
