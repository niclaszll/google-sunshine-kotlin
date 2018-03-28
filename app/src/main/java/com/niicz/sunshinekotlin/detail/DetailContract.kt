package com.niicz.sunshinekotlin.detail

import com.niicz.sunshinekotlin.BasePresenter
import com.niicz.sunshinekotlin.BaseView

interface DetailContract {

    interface View : BaseView<Presenter> {
        fun showWeatherDetails(forecastEnvelope: String)
    }

    interface Presenter : BasePresenter<View> {
        override fun takeView(view: DetailContract.View)
        override fun dropView()
    }
}