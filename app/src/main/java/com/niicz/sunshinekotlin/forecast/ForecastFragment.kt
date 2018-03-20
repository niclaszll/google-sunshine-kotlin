package com.niicz.sunshinekotlin.forecast

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.data.room.WeatherContract
import com.niicz.sunshinekotlin.detail.DetailActivity
import com.niicz.sunshinekotlin.di.ActivityScoped
import com.niicz.sunshinekotlin.util.ItemClickSupport
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.list_item_forecast.view.*
import javax.annotation.Nullable
import javax.inject.Inject

@ActivityScoped
class ForecastFragment @Inject constructor(): DaggerFragment(), ForecastContract.View {

    @Inject
    lateinit var presenter: ForecastContract.Presenter

    private lateinit var linearLayoutManager: LinearLayoutManager
    private var forecastAdapter: ForecastAdapter? = null
    private lateinit var forecastData: MutableList<WeatherContract.WeatherEntry>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.forecastfragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        return if (id == R.id.action_refresh) {
            refreshWeather()
            return true
        } else super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_forecast, container, false)
        val forecastRecyclerView = rootView.findViewById(R.id.recyclerViewForecast) as RecyclerView

        linearLayoutManager = LinearLayoutManager(activity)
        forecastRecyclerView.layoutManager = linearLayoutManager

        forecastData = mutableListOf()
        forecastAdapter = ForecastAdapter(forecastData)
        forecastRecyclerView.adapter = forecastAdapter

        ItemClickSupport.addTo(forecastRecyclerView).setOnItemClickListener(
            object : ItemClickSupport.OnItemClickListener {
                override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                    val forecast = linearLayoutManager.findViewByPosition(position)
                        .list_item_forecast_textview.text
                    val intent = Intent(activity, DetailActivity::class.java)
                        .putExtra(Intent.EXTRA_TEXT, forecast)
                    startActivity(intent)
                }
            }
        )
        return rootView
    }

    override fun showWeather(entries: MutableList<WeatherContract.WeatherEntry>) {
        forecastAdapter?.replaceData(entries)
    }

    override fun refreshWeather() {
        presenter.fetchWeather()
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

}
