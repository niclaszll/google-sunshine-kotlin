package com.niicz.sunshinekotlin.forecast

import android.net.Uri
import android.os.AsyncTask
import android.text.format.Time
import android.util.Log
import com.niicz.sunshinekotlin.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URL
import org.json.JSONObject
import org.json.JSONException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.R.attr.timeZone


class ForecastPresenter(private val forecastView: ForecastContract.View) :
    ForecastContract.Presenter {

    init {
        forecastView.presenter = this
    }

    override fun fetchWeather(location:String) {
        val weatherTask = FetchWeatherTask()
        weatherTask.execute(location)
    }

    //sample data - will be removed later
    override fun getSampleData(): MutableList<String> {
        return mutableListOf(
            "Today - Sunny - 30",
            "Tomorrow - Sunny - 28",
            "Weds - Cloudy - 20",
            "Thurs - Sunny - 25",
            "Fri - Sunny - 26",
            "Sat - Rainy - 20",
            "Sun - Rainy - 21"
        )
    }

    inner class FetchWeatherTask : AsyncTask<String, Void, MutableList<String>>() {

        private val logTag = FetchWeatherTask::class.java.simpleName


        private fun getReadableDateString(time: Long): String {
            val shortenedDateFormat = SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.US)
            return shortenedDateFormat.format(time)
        }

        private fun formatHighLows(high: Double, low: Double): String {
            val roundedHigh = Math.round(high)
            val roundedLow = Math.round(low)

            return roundedHigh.toString() + "/" + roundedLow
        }

        @Throws(JSONException::class)
        private fun getWeatherDataFromJson(forecastJsonStr: String?, numDays: Int): MutableList<String> {

            //JSON object names
            val owmList = "list"
            val owmWeather = "weather"
            val owmMain = "main"
            val owmMax = "temp_max"
            val owmMin = "temp_min"
            val owmDescription = "description"

            val forecastJson = JSONObject(forecastJsonStr)
            val weatherArray = forecastJson.getJSONArray(owmList)

            //TODO change Time(deprecated)!
            var dayTime = Time()
            dayTime.setToNow()
            val julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff)
            dayTime = Time()

            val resultStrs: MutableList<String> = LinkedList()

            for (i in 0 until weatherArray.length()) {

                val day: String
                val description: String
                val highAndLow: String

                val dayForecast = weatherArray.getJSONObject(i)
                val dateTime: Long = dayTime.setJulianDay(julianStartDay + i)

                day = getReadableDateString(dateTime)

                // description
                val weatherObject = dayForecast.getJSONArray(owmWeather).getJSONObject(0)
                description = weatherObject.getString(owmDescription)

                // temperature
                val temperatureObject = dayForecast.getJSONObject(owmMain)
                val high = temperatureObject.getDouble(owmMax)
                val low = temperatureObject.getDouble(owmMin)

                highAndLow = formatHighLows(high, low)
                resultStrs.add("$day - $description - $highAndLow")
            }

            return resultStrs

        }

        override fun doInBackground(vararg p0: String): MutableList<String> {

            var forecastJsonStr: String? = null
            val format = "json"
            val units = "metric"
            val numDays = 7

            val builtUri = Uri.parse("http://api.openweathermap.org/data/2.5/forecast?").buildUpon()
                .appendQueryParameter("q", p0[0])
                .appendQueryParameter("mode", format)
                .appendQueryParameter("units", units)
                .appendQueryParameter("cnt", Integer.toString(numDays))
                .appendQueryParameter("appid", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                .build()

            val url = URL(builtUri.toString())

            try {
                val client = OkHttpClient()
                val request: Request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                forecastJsonStr = response.body().string()

            } catch (e: IOException) {
                Log.e(logTag, e.message, e)
                e.printStackTrace()
            }

            try {
                return getWeatherDataFromJson(forecastJsonStr, numDays)

            } catch (e: JSONException) {
                Log.e(logTag, e.message, e)
                e.printStackTrace()
            }
            //if error
            return mutableListOf()

        }

        override fun onPostExecute(result: MutableList<String>) {
            if (result.isNotEmpty()) {
                forecastView.addToAdapter(result)
            }
        }

    }
}

