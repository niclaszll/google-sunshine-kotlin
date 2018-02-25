package com.niicz.sunshinekotlin.forecast

import com.niicz.sunshinekotlin.di.ActivityScoped
import com.niicz.sunshinekotlin.network.FetchWeatherTask
import javax.annotation.Nullable
import javax.inject.Inject
import javax.inject.Provider

@ActivityScoped
class ForecastPresenter @Inject constructor() : ForecastContract.Presenter, FetchWeatherTask.AsyncResponse {

    @Inject
    lateinit var fetchWeatherTaskProvider: Provider<FetchWeatherTask>

    @Nullable
    private var forecastView: ForecastContract.View? = null

    override fun fetchWeather(location: String, unitType: String) {
        //AsyncTask response
        val fetchWeatherTask = fetchWeatherTaskProvider.get()
        fetchWeatherTask.delegate = this
        fetchWeatherTask.execute(location, unitType)
    }

    override fun addToAdapter(result: MutableList<String>) {
        forecastView?.addToAdapter(result)
    }

    override fun takeView(view: ForecastContract.View) {
        this.forecastView = view
    }

    override fun dropView() {
        forecastView = null
    }

    //AsyncResponse call
    override fun processFinish(output: MutableList<String>) {
        addToAdapter(output)
    }
}

