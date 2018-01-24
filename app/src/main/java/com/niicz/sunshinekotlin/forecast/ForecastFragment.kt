package com.niicz.sunshinekotlin.forecast

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.detail.DetailActivity
import com.niicz.sunshinekotlin.util.ItemClickSupport
import kotlinx.android.synthetic.main.list_item_forecast.view.*


class ForecastFragment : Fragment(), ForecastContract.View {

    override lateinit var presenter: ForecastContract.Presenter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var forecastAdapter: ForecastAdapter? = null
    private lateinit var forecastData: MutableList<String>

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater!!.inflate(R.layout.fragment_forecast, container, false)

        val forecastRecyclerView = rootView.findViewById(R.id.recyclerViewForecast) as RecyclerView

        linearLayoutManager = LinearLayoutManager(activity)
        forecastRecyclerView.layoutManager = linearLayoutManager

        forecastData = mutableListOf()
        forecastAdapter = ForecastAdapter(forecastData)
        forecastRecyclerView.adapter = forecastAdapter

        ItemClickSupport.addTo(forecastRecyclerView).setOnItemClickListener(
            object : ItemClickSupport.OnItemClickListener {
                override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                    val forecast = linearLayoutManager.findViewByPosition(position).list_item_forecast_textview.text
                    val intent = Intent(activity, DetailActivity::class.java)
                        .putExtra(Intent.EXTRA_TEXT, forecast)
                    startActivity(intent)
                }
            }
        )
        return rootView
    }

    override fun refreshWeather() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val location = prefs.getString(
            getString(R.string.pref_location_key),
            getString(R.string.pref_location_default)
        )
        val unitType = prefs.getString(
            getString(R.string.pref_units_key),
            getString(R.string.pref_units_metric)
        )
        presenter.fetchWeather(location, unitType)
    }

    override fun addToAdapter(dayForecastStrs: MutableList<String>) {
        forecastData.clear()
        forecastData.addAll(dayForecastStrs)
        forecastAdapter!!.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        refreshWeather()
    }

    companion object {

        fun newInstance(): ForecastFragment {
            return ForecastFragment()
        }
    }

}
