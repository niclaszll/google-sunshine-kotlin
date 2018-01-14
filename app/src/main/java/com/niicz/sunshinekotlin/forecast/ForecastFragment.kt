package com.niicz.sunshinekotlin.forecast

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ArrayAdapter
import com.niicz.sunshinekotlin.R
import kotlinx.android.synthetic.main.fragment_forecast.view.*


class ForecastFragment : Fragment(), ForecastContract.View {

    override lateinit var presenter: ForecastContract.Presenter

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

        val forecastAdapter = ArrayAdapter<String>(activity, R.layout.list_item_forecast, R.id.list_item_forecast_textview, presenter.getSampleData())

        return inflater!!.inflate(R.layout.fragment_forecast, container, false).apply {
            listview_forecast.adapter = forecastAdapter
        }
    }

    override fun refreshWeather() {
        presenter.fetchWeather()
    }

    companion object {

        fun newInstance(): ForecastFragment {
            return ForecastFragment()
        }
    }

}
