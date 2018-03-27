package com.niicz.sunshinekotlin.data.repository

import com.niicz.sunshinekotlin.data.room.WeatherEntry
import io.reactivex.Flowable
import javax.inject.Inject


class WeatherRepository @Inject constructor(@Local private var localDataSource: WeatherDataSource, @Remote private var remoteDataSource: WeatherDataSource) :
    WeatherDataSource {

    override fun getWeatherEntries(location: String, forceRemote: Boolean): Flowable<MutableList<WeatherEntry>> {

        return if (forceRemote) {
            refreshData(location)
        } else {
            // else return data from local storage
            localDataSource.getWeatherEntries(location, false)
                .take(1)
                .flatMap(({ Flowable.fromIterable(it) }))
                .toList()
                .toFlowable()
                .filter({ list -> !list.isEmpty() })
                .switchIfEmpty(refreshData(location)) // If local data is empty, fetch from remote source instead.
        }
    }

    /**
     * Fetches data from remote source.
     * Save it into both local database and cache.
     *
     * @return the Flowable of newly fetched data.
     */
    private fun refreshData(location: String): Flowable<MutableList<WeatherEntry>> {

        return remoteDataSource.getWeatherEntries(location, true).doOnNext({

            // Clear data in local storage
            localDataSource.deleteAllWeatherEntries()
        }).flatMap(({ Flowable.fromIterable(it) })).doOnNext({ entry ->
            val entryID = localDataSource.insertWeatherEntry(entry)
            entry.id = entryID
        }).toList().toFlowable()
    }

    override fun getWeatherEntryById(wID: Long): Flowable<WeatherEntry> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveWeatherEntries(weatherEntries: List<WeatherEntry>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllWeatherEntries() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertWeatherEntry(weatherEntry: WeatherEntry): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}