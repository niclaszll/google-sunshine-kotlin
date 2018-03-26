package com.niicz.sunshinekotlin.detail

import com.niicz.sunshinekotlin.BasePresenter
import com.niicz.sunshinekotlin.BaseView
import com.niicz.sunshinekotlin.data.room.WeatherEntry

interface DetailContract {

    interface View : BaseView<Presenter> {
        fun showWeatherDetails(entry: WeatherEntry)
    }

    interface Presenter : BasePresenter<View> {
        override fun takeView(view: DetailContract.View)
        override fun dropView()
        fun getWeatherEntryById(wID: Long)
    }
}