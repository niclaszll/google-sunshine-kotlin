package com.niicz.sunshinekotlin.detail

import com.niicz.sunshinekotlin.BasePresenter
import com.niicz.sunshinekotlin.BaseView

interface DetailContract {

    interface View : BaseView<Presenter>{}

    interface Presenter : BasePresenter{}
}