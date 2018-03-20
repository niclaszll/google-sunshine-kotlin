package com.niicz.sunshinekotlin.forecast

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.data.room.WeatherContract
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class ForecastAdapter(private val forecastList: MutableList<WeatherContract.WeatherEntry>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {


    private val onClickSubject = PublishSubject.create<WeatherContract.WeatherEntry>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ForecastViewHolder {
        val v =
            LayoutInflater.from(parent?.context)
                .inflate(R.layout.list_item_forecast, parent, false)
        return ForecastViewHolder(v)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder?, position: Int) {
        val entry: WeatherContract.WeatherEntry = forecastList[position]
        val text = entry.weather!![0].description + entry.main!!.min
        holder!!.itemView.setOnClickListener { onClickSubject.onNext(entry) }
        holder.forecastTextView.text = text
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    fun replaceData(questions: MutableList<WeatherContract.WeatherEntry>) {
        this.forecastList.clear()
        this.forecastList.addAll(questions)
        notifyDataSetChanged()
    }

    fun clearData() {
        forecastList.clear()
        notifyDataSetChanged()
    }

    fun getPositionClicks(): Observable<WeatherContract.WeatherEntry> {
        return onClickSubject as Observable<WeatherContract.WeatherEntry>
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var forecastTextView: TextView = itemView.findViewById(R.id.list_item_forecast_textview)

    }
}