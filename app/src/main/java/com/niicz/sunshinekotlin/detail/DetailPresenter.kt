package com.niicz.sunshinekotlin.detail

import com.niicz.sunshinekotlin.data.repository.WeatherRepository
import com.niicz.sunshinekotlin.di.ActivityScoped
import com.niicz.sunshinekotlin.util.schedulers.RunOn
import com.niicz.sunshinekotlin.util.schedulers.SchedulerType
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.annotation.Nullable
import javax.inject.Inject


@ActivityScoped
class DetailPresenter @Inject constructor(
    var repository: WeatherRepository,
    @RunOn(SchedulerType.IO) private var ioScheduler: Scheduler,
    @RunOn(SchedulerType.UI) private var uiScheduler: Scheduler
) : DetailContract.Presenter {

    @Nullable
    var detailView: DetailContract.View? = null

    private var disposeBag: CompositeDisposable = CompositeDisposable()

    override fun takeView(view: DetailContract.View) {
        this.detailView = view
    }

    override fun dropView() {
        disposeBag.clear()
        detailView = null
    }

}

