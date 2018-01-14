package com.spicywdev.sunshinekotlin.forecast

import com.spicywdev.sunshinekotlin.BasePresenter
import com.spicywdev.sunshinekotlin.BaseView


interface ForecastContract {

    interface View : BaseView<Presenter> {

        fun refreshWeather()
    }

    interface Presenter : BasePresenter {

        fun fetchWeather()

        fun getSampleData(): List<String>
    }
}