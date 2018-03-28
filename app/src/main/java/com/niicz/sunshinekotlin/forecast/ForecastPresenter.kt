package com.niicz.sunshinekotlin.forecast

import android.util.Log
import com.niicz.sunshinekotlin.data.repository.WeatherForecastEnvelope
import com.niicz.sunshinekotlin.data.repository.WeatherRepository
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


    /**
     * Fetches Weather from local/remote DB
     */
    override fun fetchWeather(location: String) {

        forecastView?.clearForecast()

        val disposable = repository.getWeatherForecast(location)
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(
                { entries -> handleReturnedData(entries.forecast) },
                { error -> handleError(error) },
                { forecastView?.stopLoadingIndicator() }
            )
        disposeBag.add(disposable)
    }

    override fun takeView(view: ForecastContract.View) {
        this.forecastView = view
    }

    override fun dropView() {
        disposeBag.clear()
        forecastView = null
    }

    /**
     * Updates view after loading data is completed successfully.
     */
    private fun handleReturnedData(list: MutableList<WeatherForecastEnvelope.ForecastData>) {
        forecastView?.stopLoadingIndicator()
        if (!list.isEmpty()) {
            forecastView?.showWeather(list)
        } else {
            forecastView?.showNoDataMessage()
        }
    }

    /**
     * Updates view if there is an error after loading data from repository.
     */
    private fun handleError(error: Throwable) {
        forecastView?.stopLoadingIndicator()
        Log.e("Presenter", error.localizedMessage)
    }

}

