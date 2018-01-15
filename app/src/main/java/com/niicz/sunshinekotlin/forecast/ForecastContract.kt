package com.niicz.sunshinekotlin.forecast

import com.niicz.sunshinekotlin.BasePresenter
import com.niicz.sunshinekotlin.BaseView


interface ForecastContract {

    interface View : BaseView<Presenter> {

        fun refreshWeather()
        fun clearAdapter()
        fun addToAdapter(dayForecastStr: String)
    }

    interface Presenter : BasePresenter {

        fun fetchWeather()

        fun getSampleData(): List<String>
    }
}