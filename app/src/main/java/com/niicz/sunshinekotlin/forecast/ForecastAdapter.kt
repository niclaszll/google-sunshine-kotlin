package com.niicz.sunshinekotlin.forecast

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.niicz.sunshinekotlin.R


class ForecastAdapter(private val forecastList : MutableList<String>) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_forecast, parent, false)


        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.forecastTextView?.text = forecastList[position]
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var forecastTextView: TextView = itemView.findViewById(R.id.list_item_forecast_textview)

    }
}