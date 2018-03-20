package com.niicz.sunshinekotlin.forecast

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.niicz.sunshinekotlin.BaseRecyclerViewAdapter
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.data.room.WeatherContract


class ForecastAdapter(private val forecastList: MutableList<WeatherContract.WeatherEntry>) :
    BaseRecyclerViewAdapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ForecastViewHolder {
        val v =
            LayoutInflater.from(parent?.context).inflate(R.layout.list_item_forecast, parent, false)


        return ForecastViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        super.onBindViewHolder(viewHolder, i)
        //holder?.forecastTextView?.text = forecastList[position]
        val vh: ForecastViewHolder = viewHolder as ForecastViewHolder
        val entry: WeatherContract.WeatherEntry = forecastList[i]
        vh.forecastTextView.text = entry.weather!![0].description
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    fun replaceData(questions: MutableList<WeatherContract.WeatherEntry>) {
        this.forecastList.clear()
        this.forecastList.addAll(questions)
        notifyDataSetChanged()
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var forecastTextView: TextView = itemView.findViewById(R.id.list_item_forecast_textview)

    }
}