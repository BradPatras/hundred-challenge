package io.github.bradpatras.hundredchallenge.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import io.github.bradpatras.hundredchallenge.R
import kotlinx.android.synthetic.main.timer_view.view.*
import java.text.DecimalFormat

private const val TIME_PLACEHOLDER: String = "00"

class TimerView : ConstraintLayout {

    private val timerFormat = DecimalFormat("00")

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.timer_view, this)
    }

    fun setTime(minutes: Int?, seconds: Int?) {
        setMinutes(minutes)
        setSeconds(seconds)
    }

    private fun setMinutes(minutes: Int?) {
        if (minutes is Int) {
            minutes_tv.text = timerFormat.format(minutes)
        } else {
            minutes_tv.text = TIME_PLACEHOLDER
        }
    }

    private fun setSeconds(seconds: Int?) {
        if (seconds is Int) {
            seconds_tv.text = timerFormat.format(seconds)
        } else {
            seconds_tv.text = TIME_PLACEHOLDER
        }
    }
}
