package com.niicz.sunshinekotlin.forecast

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.data.repository.WeatherForecastEnvelope
import com.niicz.sunshinekotlin.detail.DetailActivity
import com.niicz.sunshinekotlin.di.ActivityScoped
import com.niicz.sunshinekotlin.util.WeatherDataFormatter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_forecast.*
import javax.inject.Inject
import android.support.v7.widget.DividerItemDecoration



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

        //custom divider
        /*val dividerItemDecoration = DividerItemDecoration(
            recyclerViewForecast.context,
            linearLayoutManager.orientation
        )
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context!!,R.drawable.divider_item_deco_custom)!!)
        recyclerViewForecast.addItemDecoration(dividerItemDecoration)*/

        forecastAdapter = ForecastAdapter(mutableListOf())
        recyclerViewForecast.adapter = forecastAdapter

        refreshLayoutForecast.setOnRefreshListener { this.refreshWeather() }

        //onclicklistener using rxjava
        //https://stackoverflow.com/questions/24885223/why-doesnt-recyclerview-have-onitemclicklistener
        forecastAdapter.getPositionClicks().subscribe(
            { entry ->
                startActivity(
                    Intent(activity, DetailActivity::class.java)
                        .putExtra("detail", entry)
                )
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

    override fun showWeather(forecasts: MutableList<WeatherForecastEnvelope.ForecastData>) {
        //format data
        for (item in forecasts) {
            formatWeatherData(item)
        }
        forecastAdapter.replaceData(forecasts)
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

    private fun formatWeatherData(data: WeatherForecastEnvelope.ForecastData) {

        val dataFormatter = WeatherDataFormatter()
        data.date = dataFormatter.format(data.date.toLong())

        val unitType = sharedPreferences.getString(
            getString(R.string.pref_units_key),
            getString(R.string.pref_units_metric)
        )

        data.main!!.max = dataFormatter.formatTemp(unitType, data.main!!.max)
        data.main!!.min = dataFormatter.formatTemp(unitType, data.main!!.min)
    }

}
