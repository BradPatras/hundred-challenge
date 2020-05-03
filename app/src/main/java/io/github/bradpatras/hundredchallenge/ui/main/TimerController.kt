package io.github.bradpatras.hundredchallenge.ui.main

import android.animation.ObjectAnimator
import androidx.core.animation.doOnEnd
import io.github.bradpatras.hundredchallenge.views.TimerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.timer_view.view.*
import java.util.concurrent.TimeUnit

class TimerController() {
    private val disposables = CompositeDisposable()
    private var timeInSeconds: Long = 0
    private var isStarted = false
    private var timerView: TimerView? = null

    fun linkToView(timerView: TimerView) {
        timerView.setOnClickListener { timerClicked() }
        timerView.button_container.setOnClickListener { resetButtonClicked() }
        this.timerView = timerView
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

    private fun resetTimer() {
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
        timerView?.setTime(minutes, seconds)
    }

    private fun timerClicked() {
        if (isStarted) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun resetButtonClicked() {
        resetTimer()
        ObjectAnimator.ofFloat(timerView!!.reset_btn, "rotation", -65f).apply {
            duration = 100
            doOnEnd {
                ObjectAnimator.ofFloat(timerView!!.reset_btn, "rotation", 0f).apply {
                    duration = 200
                    start()
                }
            }
            start()
        }
    }
}