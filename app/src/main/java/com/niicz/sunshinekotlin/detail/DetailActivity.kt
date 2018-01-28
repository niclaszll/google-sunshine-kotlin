package com.niicz.sunshinekotlin.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.settings.SettingsActivity
import com.niicz.sunshinekotlin.util.replaceFragmentInActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class DetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var detailPresenter: DetailPresenter

    @Inject
    lateinit var detailFragmentProvider: Lazy<DetailFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var detailFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_detail) as DetailFragment?

        if (detailFragment == null) {
            // Get the fragment from dagger
            detailFragment = detailFragmentProvider.get()

            replaceFragmentInActivity(detailFragment, R.id.activity_detail)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        return if (id == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}