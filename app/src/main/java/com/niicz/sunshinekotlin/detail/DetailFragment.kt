package com.niicz.sunshinekotlin.detail

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.ShareActionProvider
import android.view.*
import android.widget.TextView
import com.niicz.sunshinekotlin.R


class DetailFragment : Fragment(), DetailContract.View {

    override lateinit var presenter: DetailContract.Presenter
    private val forecastShareHashtag = " #SunshineApp"
    private var forecastStr: String? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_detail, container, false)

        setHasOptionsMenu(true)

        val intent = activity.intent
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT)
            (rootView.findViewById(R.id.detail_text) as TextView).text = forecastStr
        }

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.detailfragment, menu)
        val menuItem = menu.findItem(R.id.action_share)

        // Get the provider and hold onto it to set/change the share intent.
        val mShareActionProvider = MenuItemCompat.getActionProvider(menuItem) as ShareActionProvider

        // Attach an intent to this ShareActionProvider.  You can update this at any time,
        // like when the user selects a new piece of data they might like to share.
        mShareActionProvider.setShareIntent(createShareForecastIntent())
    }

    private fun createShareForecastIntent(): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            forecastStr + forecastShareHashtag
        )
        return shareIntent
    }

    companion object {

        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

}
