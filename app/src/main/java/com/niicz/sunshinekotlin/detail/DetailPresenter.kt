package com.niicz.sunshinekotlin.detail

import com.niicz.sunshinekotlin.di.ActivityScoped
import javax.annotation.Nullable
import javax.inject.Inject

@ActivityScoped
class DetailPresenter @Inject constructor() : DetailContract.Presenter {

    @Nullable
    private var detailView: DetailContract.View? = null

    override fun takeView(view: DetailContract.View) {
        this.detailView = view
    }

    override fun dropView() {
        detailView = null
    }

}

