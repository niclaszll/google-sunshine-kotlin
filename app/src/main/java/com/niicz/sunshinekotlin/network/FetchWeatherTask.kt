package com.niicz.sunshinekotlin.network

/*
class FetchWeatherTask @Inject constructor(private val client: OkHttpClient, private val sharedPreferences: SharedPreferences, private val locationDao: LocationDao, private val weatherDao: WeatherDao, private val repo: WeatherRepository) :
    AsyncTask<String, Void, MutableList<String>>() {

    private val logTag = FetchWeatherTask::class.java.simpleName

    // needed for return in onPostExecute
    //https://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
    interface AsyncResponse {
        fun processFinish(output: MutableList<String>)
    }

    var delegate: AsyncResponse? = null

    private fun getReadableDateString(time: Long): String {
        //TODO change to other dateformat, time gets displayed every 3h
        val shortenedDateFormat = SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.US)
        return shortenedDateFormat.format(time)
    }

    private fun formatHighLows(high: Double, low: Double): String {

        //get unitType from sharedPrefs
        val unitType = sharedPreferences.getString(
            WeatherApplication.applicationContext().getString(R.string.pref_units_key),
            WeatherApplication.applicationContext().getString(R.string.pref_units_metric)
        )

        var newHigh: Double = high
        var newLow: Double = low

        if (unitType == "imperial") {
            newHigh = (high * 1.8) + 32
            newLow = (low * 1.8) + 32
        } else if (unitType != "metric") {
            Log.d(logTag, "Unit type not found: $unitType")
        }

        val roundedHigh = Math.round(newHigh)
        val roundedLow = Math.round(newLow)

        return roundedHigh.toString() + " | " + roundedLow
    }

    @Throws(JSONException::class)
    private fun getWeatherDataFromJson(forecastJsonStr: String?, locationSetting: String): MutableList<String> {

        //JSON object names
        val owmList = "list"
        val owmWeather = "weather"
        val owmMain = "main"
        val owmMax = "temp_max"
        val owmMin = "temp_min"
        val owmDescription = "description"
        val owmCity = "city"
        val owmCityName = "name"
        val owmCoord = "coord"
        val owmLatitude = "lat"
        val owmLongitude = "lon"
        val owmWind = "wind"
        val owmPressure = "pressure"
        val owmHumidity = "humidity"
        val owmWindspeed = "speed"
        val owmWinddirection = "deg"
        val owmWeatherID = "id"

        try {
            val forecastJson = JSONObject(forecastJsonStr)
            val weatherList = forecastJson.getJSONArray(owmList)
            val cityJson = forecastJson.getJSONObject(owmCity)
            val cityName = cityJson.getString(owmCityName)
            val cityCoord = cityJson.getJSONObject(owmCoord)
            val cityLatitude = cityCoord.getDouble(owmLatitude)
            val cityLongitude = cityCoord.getDouble(owmLongitude)

            val locationId = addLocation(locationSetting, cityName, cityLatitude, cityLongitude)

            // gets later inserted - maybe change to mutableList?
            val cVVector = Vector<ContentValues>(weatherList.length())

            //TODO change Time(deprecated)!
            var dayTime = Time()
            dayTime.setToNow()
            val julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff)
            dayTime = Time()

            for (i in 0 until weatherList.length()) {

                val dateTime: Long = dayTime.setJulianDay(julianStartDay + i / 8)

                //dayobject
                val dayForecast = weatherList.getJSONObject(i)

                // main
                val mainObject = dayForecast.getJSONObject(owmMain)
                val high = mainObject.getDouble(owmMax)
                val low = mainObject.getDouble(owmMin)
                val pressure = mainObject.getDouble(owmPressure)
                val humidity = mainObject.getInt(owmHumidity)

                // weather
                val weatherObject = dayForecast.getJSONArray(owmWeather).getJSONObject(0)
                val description = weatherObject.getString(owmDescription)
                val weatherId = weatherObject.getInt(owmWeatherID)

                //wind
                val windObject = dayForecast.getJSONObject(owmWind)
                val windSpeed = windObject.getDouble(owmWindspeed)
                val windDirection = windObject.getDouble(owmWinddirection)

                val weatherValues = ContentValues()

                weatherValues.put(COLUMN_LOC_KEY, locationId)
                weatherValues.put(COLUMN_DATE, dateTime)
                weatherValues.put(COLUMN_HUMIDITY, humidity)
                weatherValues.put(COLUMN_PRESSURE, pressure)
                weatherValues.put(COLUMN_WIND_SPEED, windSpeed)
                weatherValues.put(COLUMN_DEGREES, windDirection)
                weatherValues.put(COLUMN_MAX_TEMP, high)
                weatherValues.put(COLUMN_MIN_TEMP, low)
                weatherValues.put(COLUMN_SHORT_DESC, description)
                weatherValues.put(COLUMN_WEATHER_ID, weatherId)

                cVVector.add(weatherValues)
            }

            // add to database
            if (cVVector.size > 0) {
                for (item in cVVector) {
                    weatherDao.insert(WeatherEntry().fromContentValues(item))
                }

            }

            Log.d(logTag, "FetchWeatherTask Complete. " + cVVector.size + " Inserted")

            return convertContentValuesToUXFormat(cVVector)

        } catch (e: JSONException) {
            Log.e(logTag, e.message, e)
            e.printStackTrace()
        }

        //if error
        return mutableListOf()

    }

    //TODO maybe add check for empty
    private fun addLocation(locationSetting: String, cityName: String, lat: Double, lon: Double): Long {

        //return id if city already in db
        for (item in locationDao.getAll()) {
            if (item.city == cityName) return item.lID
        }

        //else
        //create content value from data
        val locationValues = ContentValues()
        locationValues.put(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING, locationSetting)
        locationValues.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, cityName)
        locationValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LAT, lat)
        locationValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LONG, lon)

        //insert new data
        locationDao.insert(WeatherContract.LocationEntry().fromContentValues(locationValues))

        //return id from new city
        return locationDao.getByCityName(cityName).lID
    }

    private fun convertContentValuesToUXFormat(cvv: Vector<ContentValues>): MutableList<String> {
        // return strings to keep UI functional for now
        val resultStrs = mutableListOf<String>()
        for (i in 0 until cvv.size) {
            val weatherValues = cvv.elementAt(i)
            val highAndLow = formatHighLows(
                weatherValues.getAsDouble(COLUMN_MAX_TEMP),
                weatherValues.getAsDouble(COLUMN_MIN_TEMP)
            )
            resultStrs.add(
                getReadableDateString(weatherValues.getAsLong(COLUMN_DATE)) +
                        " | " + weatherValues.getAsString(COLUMN_SHORT_DESC) +
                        " | " + highAndLow
            )
        }
        return resultStrs
    }

    override fun doInBackground(vararg p0: String): MutableList<String> {

        //get location from sharedPrefs
        val location = sharedPreferences.getString(
            WeatherApplication.applicationContext().getString(R.string.pref_location_key),
            WeatherApplication.applicationContext().getString(R.string.pref_location_default)
        )

        var forecastJsonStr: String? = null
        val format = "json"
        val units = "metric"

        val builtUri = Uri.parse("http://api.openweathermap.org/data/2.5/forecast?").buildUpon()
            .appendQueryParameter("q", location)
            .appendQueryParameter("mode", format)
            .appendQueryParameter("units", units)
            .appendQueryParameter("appid", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
            .build()

        val url = URL(builtUri.toString())

        try {
            val request: Request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            forecastJsonStr = response?.body()?.string()

        } catch (e: IOException) {
            Log.e(logTag, e.message, e)
            e.printStackTrace()
        }

        try {
            return getWeatherDataFromJson(forecastJsonStr, location)

        } catch (e: JSONException) {
            Log.e(logTag, e.message, e)
            e.printStackTrace()
        }
        //if error
        return mutableListOf()

    }

    override fun onPostExecute(result: MutableList<String>) {
        if (result.isNotEmpty()) {
            //return weather data to presenter
            delegate?.processFinish(result)
        }
    }

}
*/