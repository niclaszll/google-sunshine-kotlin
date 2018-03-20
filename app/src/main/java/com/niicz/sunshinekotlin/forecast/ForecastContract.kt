package com.niicz.sunshinekotlin.forecast

import com.niicz.sunshinekotlin.BasePresenter
import com.niicz.sunshinekotlin.BaseView
import com.niicz.sunshinekotlin.data.room.WeatherContract


interface ForecastContract {

    interface View : BaseView<Presenter> {

        fun refreshWeather()
        fun showWeather(entries: MutableList<WeatherContract.WeatherEntry>)
        fun showNoDataMessage()
        fun clearForecast()
        fun stopLoadingIndicator()
    }

    interface Presenter : BasePresenter<View> {

        fun fetchWeather(location: String)
        override fun takeView(view: ForecastContract.View)
        override fun dropView()
    }
}