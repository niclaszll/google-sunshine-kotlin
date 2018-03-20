package com.niicz.sunshinekotlin.forecast

import android.util.Log
import com.niicz.sunshinekotlin.data.repository.WeatherRepository
import com.niicz.sunshinekotlin.data.room.WeatherContract
import com.niicz.sunshinekotlin.di.ActivityScoped
import com.niicz.sunshinekotlin.util.schedulers.RunOn
import com.niicz.sunshinekotlin.util.schedulers.SchedulerType
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.annotation.Nullable
import javax.inject.Inject


@ActivityScoped
class ForecastPresenter @Inject constructor(
    var repository: WeatherRepository,
    @RunOn(SchedulerType.IO) private var ioScheduler: Scheduler,
    @RunOn(SchedulerType.UI) private var uiScheduler: Scheduler
) : ForecastContract.Presenter {

    @Nullable
    private var forecastView: ForecastContract.View? = null

    private var disposeBag: CompositeDisposable = CompositeDisposable()

    override fun fetchWeather() {
        val disposable = repository.getWeatherEntries(true)
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(
                { entries -> handleReturnedData(entries) },
                { error -> handleError(error) }
            )
        disposeBag.add(disposable)
    }

    override fun takeView(view: ForecastContract.View) {
        this.forecastView = view
    }

    override fun dropView() {
        forecastView = null
    }

    /**
     * Updates view after loading data is completed successfully.
     */
    private fun handleReturnedData(list: MutableList<WeatherContract.WeatherEntry>) {

        if (!list.isEmpty()) {
            forecastView?.showWeather(list)
        } else {
            Log.v("Presenter", "no data available")
        }
    }

    /**
     * Updates view if there is an error after loading data from repository.
     */
    private fun handleError(error: Throwable) {
        Log.e("Presenter", error.localizedMessage)
    }

}

