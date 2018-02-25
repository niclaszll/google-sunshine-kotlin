package com.niicz.sunshinekotlin.forecast

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.settings.SettingsActivity
import com.niicz.sunshinekotlin.util.replaceFragmentInActivity
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class ForecastActivity : DaggerAppCompatActivity() {

    private val logTag = ForecastActivity::class.java.simpleName

    @Inject
    lateinit var forecastFragmentProvider: Lazy<ForecastFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        var forecastFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_forecast) as ForecastFragment?

        if (forecastFragment == null) {
            // Get the fragment from dagger
            forecastFragment = forecastFragmentProvider.get()
            replaceFragmentInActivity(forecastFragment, R.id.activity_forecast)
        }
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