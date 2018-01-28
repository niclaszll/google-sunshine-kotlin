package com.niicz.sunshinekotlin.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.di.ActivityScoped
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScoped
class DetailFragment @Inject constructor(): DaggerFragment(), DetailContract.View {

    @Inject
    lateinit var presenter: DetailContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_detail, container, false)

        setHasOptionsMenu(true)

        val intent = activity?.intent
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            val forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT)
            (rootView.findViewById(R.id.detail_text) as TextView).text = forecastStr
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        presenter.dropView()
        super.onDestroy()

    }

}
