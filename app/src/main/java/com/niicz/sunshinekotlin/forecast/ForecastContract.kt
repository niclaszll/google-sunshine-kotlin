package com.niicz.sunshinekotlin.forecast

import com.niicz.sunshinekotlin.BasePresenter
import com.niicz.sunshinekotlin.BaseView


interface ForecastContract {

    interface View : BaseView<Presenter> {

        fun refreshWeather()
        fun addToAdapter(dayForecastStrs: MutableList<String>)
    }

    interface Presenter : BasePresenter {

        fun fetchWeather(location:String)
    }
}