package com.niicz.sunshinekotlin.forecast

import com.niicz.sunshinekotlin.di.ActivityScoped
import com.niicz.sunshinekotlin.network.FetchWeatherTask
import javax.annotation.Nullable
import javax.inject.Inject

@ActivityScoped
class ForecastPresenter @Inject constructor() : ForecastContract.Presenter {

    @Nullable
    var forecastView: ForecastContract.View? = null

    override fun fetchWeather(location: String, unitType: String) {
        val weatherTask = FetchWeatherTask(this)
        weatherTask.execute(location, unitType)
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
}

