package com.niicz.sunshinekotlin.forecast


import com.niicz.sunshinekotlin.network.FetchWeatherTask

class ForecastPresenter(private val forecastView: ForecastContract.View) :
    ForecastContract.Presenter {


    init {
        forecastView.presenter = this
    }

    override fun fetchWeather(location: String, unitType: String) {
        val weatherTask = FetchWeatherTask(this)
        weatherTask.execute(location, unitType)
    }

    override fun addToAdapter(result: MutableList<String>) {
        forecastView.addToAdapter(result)
    }
}

