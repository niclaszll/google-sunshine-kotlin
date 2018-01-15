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


class ForecastPresenter(private val forecastView: ForecastContract.View) : ForecastContract.Presenter {

    init {
        forecastView.presenter = this
    }

    override fun fetchWeather() {
        val weatherTask = FetchWeatherTask()
        weatherTask.execute("Dresden,de")
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

        private val log_tag = FetchWeatherTask::class.java.simpleName


        private fun getReadableDateString(time: Long): String {
            // Because the API returns a unix timestamp (measured in seconds),
            // it must be converted to milliseconds in order to be converted to valid date.
            val shortenedDateFormat = SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.US)
            return shortenedDateFormat.format(time)
        }

        private fun formatHighLows(high: Double, low: Double): String {
            // For presentation, assume the user doesn't care about tenths of a degree.
            val roundedHigh = Math.round(high)
            val roundedLow = Math.round(low)

            return roundedHigh.toString() + "/" + roundedLow
        }

        @Throws(JSONException::class)
        private fun getWeatherDataFromJson(forecastJsonStr: String?, numDays: Int): MutableList<String> {

            // These are the names of the JSON objects that need to be extracted.
            val OWM_LIST = "list"
            val OWM_WEATHER = "weather"
            val OWM_TEMPERATURE = "main"
            val OWM_MAX = "temp_max"
            val OWM_MIN = "temp_min"
            val OWM_DESCRIPTION = "description"

            val forecastJson = JSONObject(forecastJsonStr)
            val weatherArray = forecastJson.getJSONArray(OWM_LIST)

            // OWM returns daily forecasts based upon the local time of the city that is being
            // asked for, which means that we need to know the GMT offset to translate this data
            // properly.

            // Since this data is also sent in-order and the first day is always the
            // current day, we're going to take advantage of that to get a nice
            // normalized UTC date for all of our weather.

            //TODO change Time to GregorianCal!
            var dayTime = Time()
            dayTime.setToNow()

            // we start at the day returned by local time. Otherwise this is a mess.
            val julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff)

            // now we work exclusively in UTC
            dayTime = Time()

            val resultStrs : MutableList<String> = LinkedList()

            for (i in 0 until weatherArray.length()) {
                // For now, using the format "Day, description, hi/low"
                val day: String
                val description: String
                val highAndLow: String

                // Get the JSON object representing the day
                val dayForecast = weatherArray.getJSONObject(i)

                // The date/time is returned as a long.  We need to convert that
                // into something human-readable, since most people won't read "1400356800" as
                // "this saturday".
                val dateTime: Long
                // Cheating to convert this to UTC time, which is what we want anyhow
                dateTime = dayTime.setJulianDay(julianStartDay + i)
                day = getReadableDateString(dateTime)

                // description is in a child array called "weather", which is 1 element long.
                val weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0)
                description = weatherObject.getString(OWM_DESCRIPTION)

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                val temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE)
                val high = temperatureObject.getDouble(OWM_MAX)
                val low = temperatureObject.getDouble(OWM_MIN)

                highAndLow = formatHighLows(high, low)
                resultStrs.add("$day - $description - $highAndLow")
            }

            for (s in resultStrs) {
                Log.v(log_tag, "Forecast entry: " + s)
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
                    .appendQueryParameter("APPID", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                    .build()

            val url = URL(builtUri.toString())

            Log.v("Built URI ", builtUri.toString())

            try {

                val client = OkHttpClient()
                val request: Request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                forecastJsonStr = response.body().string()

            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                return getWeatherDataFromJson(forecastJsonStr, numDays)

            } catch (e: JSONException) {
                Log.e(log_tag, e.message, e)
                e.printStackTrace()
            }
            return mutableListOf()

        }

        override fun onPostExecute(result: MutableList<String>) {

            if (result.isNotEmpty()) {
                forecastView.clearAdapter()
                for (dayForecastStr in result) {
                    forecastView.addToAdapter(dayForecastStr)
                }
            }

        }
    }

}
