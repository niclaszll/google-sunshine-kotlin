package com.spicywdev.sunshinekotlin.forecast

import android.os.AsyncTask
import android.util.Log
import com.spicywdev.sunshinekotlin.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URL

class ForecastPresenter(private val forecastView: ForecastContract.View) : ForecastContract.Presenter {

    init {
        forecastView.presenter = this
    }

    override fun fetchWeather() {
        val weatherTask = FetchWeatherTask()
        weatherTask.execute()
    }

    //sample data - will be removed later
    override fun getSampleData(): List<String> {
        return listOf(
                "Today - Sunny - 30",
                "Tomorrow - Sunny - 28",
                "Weds - Cloudy - 20",
                "Thurs - Sunny - 25",
                "Fri - Sunny - 26",
                "Sat - Rainy - 20",
                "Sun - Rainy - 21"
        )
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


}