package com.niicz.sunshinekotlin.detail

class DetailPresenter(private val detailView: DetailContract.View) :
    DetailContract.Presenter {

    init {
        detailView.presenter = this
    }

}

