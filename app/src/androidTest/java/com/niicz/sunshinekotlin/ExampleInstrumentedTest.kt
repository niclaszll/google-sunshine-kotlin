package com.niicz.sunshinekotlin

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.niicz.sunshinekotlin.data.WeatherContract
import com.niicz.sunshinekotlin.data.WeatherDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class DatabaseTest {

    private lateinit var weatherDatabase: WeatherDatabase
    private val weatherEntry: WeatherContract.WeatherEntry =
        WeatherContract.WeatherEntry(1, 1, "", "", 1.0, 1.0, 1.0, 1.0, 1.0, "0")
    private val locationEntry : WeatherContract.LocationEntry = WeatherContract.LocationEntry(1, "", "", "", "")
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

    @Test
    fun insertAllSavesData() {

        weatherDatabase.locationDao().insertAll(listOf(locationEntry))
        weatherDatabase.weatherDao().insertAll(listOf(weatherEntry))

        val entries = weatherDatabase.weatherDao().getAll()
        assertEquals(entries[0], weatherEntry)

    }

    @Test
    fun clearWeatherTableClearsAll() {

        weatherDatabase.locationDao().insertAll(listOf(locationEntry))
        weatherDatabase.weatherDao().insertAll(listOf(weatherEntry))

        assertTrue(weatherDatabase.weatherDao().getAll().isNotEmpty())

        weatherDatabase.weatherDao().clearWeatherTable()

        assertTrue(weatherDatabase.weatherDao().getAll().isEmpty())

    }
}
