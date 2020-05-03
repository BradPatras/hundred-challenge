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

    private val secondsFormat = DecimalFormat("00")
    private var _labelText: String? = ""

    // The text below the time labels
    var labelText: String?
        get() = _labelText
        set(value) {
            _labelText = value
            setLabel(value ?: "")
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        View.inflate(context, R.layout.timer_view, this)
        val labelAttribute = context.obtainStyledAttributes(attrs, R.styleable.TimerView, defStyle, 0)
        labelText = labelAttribute.getString(R.styleable.TimerView_labelText)
        labelAttribute.recycle()

    }

    fun setTime(minutes: Int?, seconds: Int?) {
        setMinutes(minutes)
        setSeconds(seconds)
    }

    private fun setMinutes(minutes: Int?) {
        if (minutes is Int) {
            val displayMinutes = if (minutes < 10) "0$minutes" else "$minutes"
            minutes_tv.text = displayMinutes
        } else {
            minutes_tv.text = TIME_PLACEHOLDER
        }
    }

    private fun setSeconds(seconds: Int?) {
        if (seconds is Int) {
            val displaySeconds = if (seconds < 10) "0$seconds" else "$seconds"
            seconds_tv.text = secondsFormat.format(seconds)
        } else {
            seconds_tv.text = TIME_PLACEHOLDER
        }
    }

    fun setLabel(label: String) {
        label_tv.text = label
    }
}
