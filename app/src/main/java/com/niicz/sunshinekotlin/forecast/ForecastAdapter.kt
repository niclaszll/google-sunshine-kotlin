package com.niicz.sunshinekotlin.forecast

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.data.repository.WeatherForecastEnvelope
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class ForecastAdapter(private val forecastList: MutableList<WeatherForecastEnvelope.ForecastData>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_forecast, parent, false)
        return ForecastViewHolder(v)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast: WeatherForecastEnvelope.ForecastData = forecastList[position]

        holder.itemView.setOnClickListener { onClickSubject.onNext(forecast) }

        holder.forecastDescriptionView.text = forecast.weather!![0].description
        holder.forecastMaxTempView.text = forecast.main!!.max.toInt().toString()
        holder.forecastMinTempView.text = forecast.main!!.min.toInt().toString()
        holder.forecastDateView.text = forecast.date
    }

    private val onClickSubject = PublishSubject.create<WeatherForecastEnvelope.ForecastData>()

    override fun getItemCount(): Int {
        return forecastList.size
    }

    fun replaceData(forecasts: MutableList<WeatherForecastEnvelope.ForecastData>) {
        this.forecastList.clear()
        this.forecastList.addAll(forecasts)
        notifyDataSetChanged()
    }

    fun clearData() {
        forecastList.clear()
        notifyDataSetChanged()
    }

    fun getPositionClicks(): Observable<WeatherForecastEnvelope.ForecastData> {
        return onClickSubject as Observable<WeatherForecastEnvelope.ForecastData>
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var forecastDescriptionView: TextView = itemView.findViewById(R.id.list_item_forecast_description)
        var forecastMaxTempView: TextView = itemView.findViewById(R.id.list_item_forecast_max)
        var forecastMinTempView: TextView = itemView.findViewById(R.id.list_item_forecast_min)
        var forecastDateView: TextView = itemView.findViewById(R.id.list_item_forecast_date)

    }
}