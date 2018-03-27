package com.niicz.sunshinekotlin.forecast

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.data.room.WeatherEntry
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class ForecastAdapter(private val forecastList: MutableList<WeatherEntry>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_forecast, parent, false)
        return ForecastViewHolder(v)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val entry: WeatherEntry = forecastList[position]
        //weather is null if forceRemote=false
        val text = entry.weather!![0].description + "  -  " + entry.main!!.min + "  -  " + entry.id
        holder.itemView.setOnClickListener { onClickSubject.onNext(entry) }
        holder.forecastTextView.text = text
    }


    private val onClickSubject = PublishSubject.create<WeatherEntry>()


    override fun getItemCount(): Int {
        return forecastList.size
    }

    fun replaceData(questions: MutableList<WeatherEntry>) {
        this.forecastList.clear()
        this.forecastList.addAll(questions)
        notifyDataSetChanged()
    }

    fun clearData() {
        forecastList.clear()
        notifyDataSetChanged()
    }

    fun getPositionClicks(): Observable<WeatherEntry> {
        return onClickSubject as Observable<WeatherEntry>
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var forecastTextView: TextView = itemView.findViewById(R.id.list_item_forecast_textview)

    }
}