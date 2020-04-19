package io.github.bradpatras.hundredchallenge.list

import android.animation.ValueAnimator
import android.view.View
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.exercise_list_item.view.*

class ExerciseViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    fun animateProgressBar(oldProgress: Double, newProgress: Double) {
        val doneAnim = ValueAnimator.ofFloat(oldProgress.toFloat(), newProgress.toFloat())
        doneAnim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Float
            updateProgressBar(value.toDouble())
        }
        doneAnim.duration = 150
        doneAnim.start()
    }

    fun updateProgressBar(progress: Double) {
        view.progress_bar.doOnLayout {
            val layoutParams = view.progress_bar.layoutParams
            (progress * view.bg_view.measuredWidth).toInt()
            val progressWidth = (progress * view.bg_view.measuredWidth).toInt()
            layoutParams.width = progressWidth
            view.progress_bar.layoutParams = layoutParams
            view.progress_bar.visibility = if (progress <= 0) View.INVISIBLE else View.VISIBLE
        }
    }
}