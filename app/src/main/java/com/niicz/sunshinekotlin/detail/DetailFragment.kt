package com.niicz.sunshinekotlin.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.niicz.sunshinekotlin.R
import android.widget.TextView
import android.content.Intent




class DetailFragment : Fragment(), DetailContract.View {

    override lateinit var presenter: DetailContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_detail, container, false)

        val intent = activity.intent
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            val forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT)
            (rootView.findViewById(R.id.detail_text) as TextView).text = forecastStr
        }

        return rootView
    }

    companion object {

        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

}
