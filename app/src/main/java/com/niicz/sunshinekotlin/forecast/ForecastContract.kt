package com.niicz.sunshinekotlin.forecast

import com.niicz.sunshinekotlin.BasePresenter
import com.niicz.sunshinekotlin.BaseView


interface ForecastContract {

    interface View : BaseView<Presenter> {

        fun refreshWeather()
        fun addToAdapter(dayForecastStrs: MutableList<String>)
    }

    interface Presenter : BasePresenter<View> {

        fun fetchWeather(location: String, unitType: String)
        fun addToAdapter(result: MutableList<String>)
        override fun takeView(view: ForecastContract.View)
        override fun dropView()
    }
}