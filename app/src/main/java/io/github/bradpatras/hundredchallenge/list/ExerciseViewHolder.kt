package io.github.bradpatras.hundredchallenge.list

import android.animation.ValueAnimator
import android.graphics.drawable.TransitionDrawable
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

            // can't compare against zero because of an apparent bug/feature of constraint layout
            // where the width is getting rounded to zero at some point when the value gets small
            // enough, causing the view to switch to "match constraints" mode, making the width jump
            if (progress < 0.02) {
                view.progress_bar.visibility = View.INVISIBLE
            } else {
                view.progress_bar.visibility = View.VISIBLE
                val layoutParams = view.progress_bar.layoutParams
                (progress * view.bg_view.measuredWidth).toInt()
                val progressWidth = (progress * view.bg_view.measuredWidth).toInt()
                layoutParams.width = progressWidth
                view.progress_bar.layoutParams = layoutParams
            }

            if (progress >= 1) {
                view.alpha = .65f
                (view.progress_bar.background as? TransitionDrawable)?.startTransition(250)
            } else {
                view.alpha = 1f
                (view.progress_bar.background as? TransitionDrawable)?.resetTransition()
            }
        }
    }
}