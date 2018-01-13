package com.spicywdev.sunshinekotlin

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_main.view.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URL


class MainActivityFragment : Fragment() {

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
            val weatherTask = FetchWeatherTask()
            weatherTask.execute()
            return true
        } else super.onOptionsItemSelected(item)
    }

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

        return inflater!!.inflate(R.layout.fragment_main, container, false).apply {
            listview_forecast.adapter = forecastAdapter
        }
    }
}

class FetchWeatherTask : AsyncTask<Void, Void, String?>() {

    override fun doInBackground(vararg p0: Void?): String? {

        var forecastJsonStr: String? = null
        val baseURL = "http://api.openweathermap.org/data/2.5/forecast?q=Dresden,de&mode=json&units=metric&cnt=7"
        val apiKey = "&appid=" + BuildConfig.OPEN_WEATHER_MAP_API_KEY
        val url = URL(baseURL + apiKey)

        try {

            val client = OkHttpClient()
            val request: Request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            forecastJsonStr = response.body().string()

        } catch (e: IOException) {
            e.printStackTrace()
        }

        Log.v("Test", url.toString())
        Log.v("Test", forecastJsonStr)
        return forecastJsonStr
    }

}
