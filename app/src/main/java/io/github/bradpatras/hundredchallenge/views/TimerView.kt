package io.github.bradpatras.hundredchallenge.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import io.github.bradpatras.hundredchallenge.R
import kotlinx.android.synthetic.main.timer_view.view.*
import java.text.DecimalFormat

private const val TIME_PLACEHOLDER: String = "--"

class PaceView : ConstraintLayout {
    enum class DistanceUnit(val abbreviation: String) {
        KM("km"),
        MI("mi")
    }

    private val secondsFormat = DecimalFormat("00")
    private var _labelText: String? = ""

    // The text above the time labels
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

    fun setPace(minutes: Int?, seconds: Int?) {
        setMinutes(minutes)
        setSeconds(seconds)
    }

    private fun setMinutes(minutes: Int?) {
        if (minutes is Int) {
            minutes_tv.text = "$minutes"
        } else {
            minutes_tv.text = TIME_PLACEHOLDER
        }
    }

    private fun setSeconds(seconds: Int?) {
        if (seconds is Int) {
            seconds_tv.text = secondsFormat.format(seconds)
        } else {
            seconds_tv.text = TIME_PLACEHOLDER
        }
    }

    fun setLabel(label: String) {
        label_tv.text = label
    }
}
