package com.niicz.sunshinekotlin.data.repository

import com.niicz.sunshinekotlin.data.room.WeatherContract
import io.reactivex.Flowable
import javax.inject.Inject


class WeatherRepository @Inject constructor(@Local var localDataSource: WeatherDataSource, @Remote var remoteDataSource: WeatherDataSource) :
    WeatherDataSource {

    private var caches: MutableList<WeatherContract.WeatherEntry> = mutableListOf()

    override fun getWeatherEntries(location: String, forceRemote: Boolean): Flowable<MutableList<WeatherContract.WeatherEntry>> {

        if (forceRemote) {
            return refreshData(location)
        } else {
            if (caches.isNotEmpty()) {
                // if cache is available, return it immediately
                return Flowable.just(caches)
            } else {
                // else return data from local storage
                return localDataSource.getWeatherEntries(location, false)
                    .take(1)
                    .flatMap(({ Flowable.fromIterable(it) }))
                    .doOnNext { question -> caches.add(question) }
                    .toList()
                    .toFlowable()
                    .filter({ list -> !list.isEmpty() })
                    .switchIfEmpty(
                        refreshData(location)
                    ) // If local data is empty, fetch from remote source instead.
            }
        }
    }

    /**
     * Fetches data from remote source.
     * Save it into both local database and cache.
     *
     * @return the Flowable of newly fetched data.
     */
    private fun refreshData(location: String): Flowable<MutableList<WeatherContract.WeatherEntry>> {

        return remoteDataSource.getWeatherEntries(location,true).doOnNext({

            // Clear cache
            caches.clear()
            // Clear data in local storage
            localDataSource.deleteAllWeatherEntries()
        }).flatMap(({ Flowable.fromIterable(it) })).doOnNext({ question ->
            caches.add(question)
            localDataSource.insertWeatherEntry(question)
        }).toList().toFlowable()
    }

    override fun saveWeatherEntries(weatherEntries: List<WeatherContract.WeatherEntry>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllWeatherEntries() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertWeatherEntry(weatherEntry: WeatherContract.WeatherEntry) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}