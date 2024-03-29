package com.niicz.sunshinekotlin.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.data.repository.WeatherForecastEnvelope
import com.niicz.sunshinekotlin.di.ActivityScoped
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

@ActivityScoped
class DetailFragment @Inject constructor() : DaggerFragment(), DetailContract.View {

    @Inject
    lateinit var presenter: DetailContract.Presenter

    lateinit var weatherData: WeatherForecastEnvelope.ForecastData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_detail, container, false)

        setHasOptionsMenu(true)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = activity?.intent
        if (intent!!.hasExtra("detail")) {
            val data = activity!!.intent.extras
            weatherData = data.getParcelable("detail")
            this.showWeatherDetails()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        presenter.dropView()
        super.onDestroy()

    }

    override fun showWeatherDetails() {
        detail_text.text = weatherData.date
    }

}
