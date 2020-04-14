package io.github.bradpatras.hundredchallenge.ui.main

import io.github.bradpatras.hundredchallenge.views.TimerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class TimerController(val timerView: TimerView) {

    private val disposables = CompositeDisposable()
    private var timeInSeconds: Long = 0
    private var isStarted = false

    init {
        timerView.setOnClickListener { timerClicked() }
        timerView.setOnLongClickListener { timerLongClicked() }
    }

    fun startTimer() {
        isStarted = true
        val ticker = Observable.interval(0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                tick()
            }

        disposables.add(ticker)
    }

    fun stopTimer() {
        isStarted = false
        disposables.clear()
    }

    fun resetTimer() {
        isStarted = false
        disposables.clear()
        timeInSeconds = 0
        updateTimerView()
    }

    private fun tick() {
        timeInSeconds += 1
        updateTimerView()
    }

    private fun updateTimerView() {
        val minutes = (timeInSeconds / 60).toInt()
        val seconds = (timeInSeconds % 60).toInt()
        timerView.setTime(minutes, seconds)
    }

    private fun timerClicked() {
        if (isStarted) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun timerLongClicked(): Boolean {
        resetTimer()
        return true
    }
}