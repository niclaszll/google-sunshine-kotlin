package com.niicz.sunshinekotlin.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.niicz.sunshinekotlin.R


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //set fragment
        /*supportFragmentManager
            .findFragmentById(R.id.fragment_settings) as SettingsFragment?
                ?: SettingsFragment.newInstance().also {
                    replaceFragmentInActivity(it, R.id.activity_settings)
                }*/

    }


}