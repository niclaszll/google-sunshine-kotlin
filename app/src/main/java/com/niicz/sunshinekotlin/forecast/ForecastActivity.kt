package com.niicz.sunshinekotlin.forecast

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.util.replaceFragmentInActivity
import com.niicz.sunshinekotlin.settings.SettingsActivity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.preference.PreferenceManager
import android.util.Log


class ForecastActivity : AppCompatActivity() {

    private val logTag = ForecastActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        //set fragment
        val forecastFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_forecast) as ForecastFragment?
                ?: ForecastFragment.newInstance().also {
                    replaceFragmentInActivity(it, R.id.activity_forecast)
                }

        //set presenter
        ForecastPresenter(forecastFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            return true
        }

        if (id == R.id.action_map) {
            openPreferredLocationInMap()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    private fun openPreferredLocationInMap() {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val location = sharedPrefs.getString(
            getString(R.string.pref_location_key),
            getString(R.string.pref_location_default)
        )

        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps
        val geoLocation = Uri.parse("geo:0,0?").buildUpon()
            .appendQueryParameter("q", location)
            .build()

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = geoLocation

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d(logTag, "Couldn't call $location, no receiving apps installed!")
        }
    }

}