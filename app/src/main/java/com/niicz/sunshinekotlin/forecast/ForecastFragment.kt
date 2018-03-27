package com.niicz.sunshinekotlin.forecast

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.data.room.WeatherEntry
import com.niicz.sunshinekotlin.detail.DetailActivity
import com.niicz.sunshinekotlin.di.ActivityScoped
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_forecast.*
import javax.inject.Inject

@ActivityScoped
class ForecastFragment @Inject constructor() : DaggerFragment(), ForecastContract.View {

    @Inject
    lateinit var presenter: ForecastContract.Presenter

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var forecastAdapter: ForecastAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    //needs to be used instead of onCreateView, else views aren't created yet
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutManager = LinearLayoutManager(activity)
        recyclerViewForecast.layoutManager = linearLayoutManager

        forecastAdapter = ForecastAdapter(mutableListOf())
        recyclerViewForecast.adapter = forecastAdapter

        refreshLayoutForecast.setOnRefreshListener { this.refreshWeather() }

        //onclicklistener using rxjava
        //https://stackoverflow.com/questions/24885223/why-doesnt-recyclerview-have-onitemclicklistener
        forecastAdapter.getPositionClicks().subscribe(
            { entry ->
                startActivity(
                    Intent(activity, DetailActivity::class.java)
                        .putExtra(Intent.EXTRA_TEXT, entry.id)
                ).also { Log.v("Test", entry.id.toString()) }
            }
        )

    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        presenter.dropView()
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        refreshWeather()
    }

    override fun clearForecast() {
        forecastAdapter.clearData()
    }

    override fun showWeather(entries: MutableList<WeatherEntry>) {
        forecastAdapter.replaceData(entries)
    }

    override fun refreshWeather() {
        //get location from sharedPrefs
        val location = sharedPreferences.getString(
            getString(R.string.pref_location_key),
            getString(R.string.pref_location_default)
        )
        presenter.fetchWeather(location)
    }

    override fun stopLoadingIndicator() {
        if (refreshLayoutForecast.isRefreshing) {
            refreshLayoutForecast.isRefreshing = false
        }
    }

    override fun showNoDataMessage() {
        Toast.makeText(activity, "No Data available.", Toast.LENGTH_SHORT).show()
    }

}
