package com.niicz.sunshinekotlin


interface BasePresenter<T> {

    fun takeView(view: T)

    fun dropView()

}