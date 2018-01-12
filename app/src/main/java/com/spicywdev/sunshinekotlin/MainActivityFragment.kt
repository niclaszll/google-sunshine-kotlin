package com.spicywdev.sunshinekotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_main.view.*


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

        return  inflater!!.inflate(R.layout.fragment_main, container, false).apply {
            listview_forecast.adapter = forecastAdapter
        }
    }
}
