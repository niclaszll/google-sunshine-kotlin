package com.niicz.sunshinekotlin.forecast

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.util.replaceFragmentInActivity


class ForecastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        //set fragment
        val forecastFragment = supportFragmentManager
                .findFragmentById(R.id.fragment_forecast) as ForecastFragment? ?:
                ForecastFragment.newInstance().also {
                    replaceFragmentInActivity(it, R.id.container)
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


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }


}