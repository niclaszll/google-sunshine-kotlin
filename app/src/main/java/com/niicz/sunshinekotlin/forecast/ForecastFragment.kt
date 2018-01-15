package com.niicz.sunshinekotlin.forecast

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import com.niicz.sunshinekotlin.R


class ForecastFragment : Fragment(), ForecastContract.View {

    override lateinit var presenter: ForecastContract.Presenter
    private var forecastAdapter: ArrayAdapter<String>? = null

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater!!.inflate(R.layout.fragment_forecast, container, false)
        forecastAdapter = ArrayAdapter(activity, R.layout.list_item_forecast, R.id.list_item_forecast_textview, presenter.getSampleData())
        val listView = rootView.findViewById(R.id.listview_forecast) as ListView
        listView.adapter = forecastAdapter

        return rootView
    }

    override fun refreshWeather() {
        presenter.fetchWeather()
    }

    override fun clearAdapter() {
        forecastAdapter!!.clear()
    }

    override fun addToAdapter(dayForecastStr: String) {
        forecastAdapter!!.add(dayForecastStr)
    }

    companion object {

        fun newInstance(): ForecastFragment {
            return ForecastFragment()
        }
    }

}
