package com.spicywdev.sunshinekotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView


class MainActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //sample data - will be removed later
        val weekForecast = listOf(
                "Today - Sunny - 30",
                "Tomorrow - Sunny - 28",
                "Weds - Cloudy - 20",
                "Thurs - Sunny - 25",
                "Fri - Sunny - 26",
                "Sat - Rainy - 20",
                "Sun - Rainy - 21"
        )

        val forecastAdapter = ArrayAdapter<String>(activity, R.layout.list_item_forecast, R.id.list_item_forecast_textview, weekForecast)

        val rootView = inflater!!.inflate(R.layout.fragment_main, container, false)

        val forecastListView = rootView.findViewById(R.id.listview_forecast) as ListView
        forecastListView.adapter = forecastAdapter

        return rootView
    }
}
