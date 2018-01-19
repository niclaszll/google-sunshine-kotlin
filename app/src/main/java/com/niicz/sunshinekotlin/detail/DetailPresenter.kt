package com.niicz.sunshinekotlin.detail

class ForecastPresenter(private val forecastView: DetailContract.View) :
    DetailContract.Presenter {

    init {
        forecastView.presenter = this
    }

}

