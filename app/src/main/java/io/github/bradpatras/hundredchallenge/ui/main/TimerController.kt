package io.github.bradpatras.hundredchallenge.ui.main

import io.github.bradpatras.hundredchallenge.views.TimerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class TimerController {
    private val disposables = CompositeDisposable()
    private var timeInSeconds: Long = 0
    private var isStarted = false
    private var timerView: TimerView? = null

    fun linkToView(timerView: TimerView) {
        timerView.setOnClickListener { timerClicked() }
        this.timerView = timerView
    }

    fun resetTimer() {
        isStarted = false
        disposables.clear()
        timeInSeconds = 0
        updateTimerView()
    }

    private fun startTimer() {
        isStarted = true
        val ticker = Observable.interval(0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                tick()
            }

        disposables.add(ticker)
    }

    private fun stopTimer() {
        isStarted = false
        disposables.clear()
    }

    private fun tick() {
        timeInSeconds += 1
        updateTimerView()
    }

    private fun updateTimerView() {
        val minutes = (timeInSeconds / 60).toInt()
        val seconds = (timeInSeconds % 60).toInt()
        timerView?.setTime(minutes, seconds)
    }

    private fun timerClicked() {
        if (isStarted) {
            stopTimer()
        } else {
            startTimer()
        }
    }
}