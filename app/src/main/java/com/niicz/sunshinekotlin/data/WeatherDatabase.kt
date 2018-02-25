package com.niicz.sunshinekotlin.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.support.annotation.VisibleForTesting


@Database(
    entities = [(WeatherContract.WeatherEntry::class), (WeatherContract.LocationEntry::class)],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
    abstract fun locationDao(): LocationDao


    companion object {


        /** The only instance  */
        private var sInstance: WeatherDatabase? = null

        /**
         * Gets the singleton instance of SampleDatabase.
         *
         * @param context The context.
         * @return The singleton instance of SampleDatabase.
         */
        @Synchronized
        fun getInstance(context: Context): WeatherDatabase {
            if (sInstance == null) {
                sInstance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        WeatherDatabase::class.java,
                        "ex"
                    )
                    .build()
            }
            return sInstance as WeatherDatabase
        }

        /**
         * Switches the internal implementation with an empty in-memory database.
         *
         * @param context The context.
         */
        @VisibleForTesting
        fun switchToInMemory(context: Context) {
            sInstance = Room.inMemoryDatabaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java
            ).build()
        }
    }
}