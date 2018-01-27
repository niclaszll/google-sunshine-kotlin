package com.niicz.sunshinekotlin.network

import android.net.Uri
import android.os.AsyncTask
import android.text.format.Time
import android.util.Log
import com.niicz.sunshinekotlin.BuildConfig
import com.niicz.sunshinekotlin.forecast.ForecastPresenter
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class FetchWeatherTask(val presenter: ForecastPresenter) : AsyncTask<String, Void, MutableList<String>>() {

    private val logTag = FetchWeatherTask::class.java.simpleName


    private fun getReadableDateString(time: Long): String {
        //TODO change to other dateformat, time gets displayed every 3h
        val shortenedDateFormat = SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.US)
        return shortenedDateFormat.format(time)
    }

    private fun formatHighLows(high: Double, low: Double, unitType: String): String {

        var newHigh: Double = high
        var newLow: Double = low

        if (unitType == "imperial") {
            newHigh = (high * 1.8) + 32
            newLow = (low * 1.8) + 32
        } else if (unitType != "metric") {
            Log.d(logTag, "Unit type not found: " + unitType)
        }

        val roundedHigh = Math.round(newHigh)
        val roundedLow = Math.round(newLow)

        return roundedHigh.toString() + "/" + roundedLow
    }

    @Throws(JSONException::class)
    private fun getWeatherDataFromJson(forecastJsonStr: String?, numDays: Int, unitType: String): MutableList<String> {

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
            val dateTime: Long = dayTime.setJulianDay(julianStartDay + i / 8)

            day = getReadableDateString(dateTime)

            // description
            val weatherObject = dayForecast.getJSONArray(owmWeather).getJSONObject(0)
            description = weatherObject.getString(owmDescription)

            // temperature
            val temperatureObject = dayForecast.getJSONObject(owmMain)
            val high = temperatureObject.getDouble(owmMax)
            val low = temperatureObject.getDouble(owmMin)

            highAndLow = formatHighLows(high, low, unitType)
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
            //.appendQueryParameter("cnt", Integer.toString(numDays))
            .appendQueryParameter("appid", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
            .build()

        val url = URL(builtUri.toString())
        Log.v("test", url.toString())

        try {
            val client = OkHttpClient()
            val request: Request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            forecastJsonStr = response?.body()?.string()

        } catch (e: IOException) {
            Log.e(logTag, e.message, e)
            e.printStackTrace()
        }

        try {
            return getWeatherDataFromJson(forecastJsonStr, numDays, p0[1])

        } catch (e: JSONException) {
            Log.e(logTag, e.message, e)
            e.printStackTrace()
        }
        //if error
        return mutableListOf()

    }

    override fun onPostExecute(result: MutableList<String>) {
        if (result.isNotEmpty()) {
            presenter.addToAdapter(result)
        }
    }

}