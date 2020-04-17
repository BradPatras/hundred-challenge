package io.github.bradpatras.hundredchallenge.list

import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.bradpatras.hundredchallenge.R
import io.github.bradpatras.hundredchallenge.data.Exercise
import kotlinx.android.synthetic.main.exercise_list_item.view.*

class ExerciseListAdapter(context: Context): RecyclerView.Adapter<ExerciseViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    var exerciseList: List<Exercise> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = layoutInflater.inflate(R.layout.exercise_list_item, parent, false)
        return ExerciseViewHolder((view))
    }

    override fun getItemCount(): Int {
       return exerciseList.count()
    }

    override fun getItemId(position: Int): Long {
        return exerciseList[position].id.toLong()
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exerciseList.getOrNull(position) ?: return
        val progressText = "${exercise.progress}/${exercise.total}"
        holder.itemView.progress_tv.text = progressText
        holder.itemView.exercise_tv.text = exercise.title
        holder.itemView.setOnClickListener { view ->
            onItemClicked(position, view)
        }
        holder.itemView.setOnLongClickListener { view ->
            onItemLongClicked(position, view)
            return@setOnLongClickListener true
        }

        val progress = exercise.progress.toDouble()/exercise.total.toDouble()
        if (progress > 0) {
            updateProgressBar(holder.itemView, progress)
        }
    }

    private fun onItemLongClicked(position: Int, view: View) {
        val clickedExercise = exerciseList.getOrNull(position) ?: return

        // Do nothing if progress is already 0
        if (clickedExercise.progress == 0) return

        clickedExercise.progress -= 5

        val updatedProgress = "${clickedExercise.progress}/${clickedExercise.total}"
        view.progress_tv.text = updatedProgress
        val progress = clickedExercise.progress.toDouble()/clickedExercise.total.toDouble()
        animateProgressBar(view, progress)
    }

    private fun onItemClicked(position: Int, view: View) {
        val clickedExercise = exerciseList.getOrNull(position) ?: return

        // Do nothing if progress is already equal to the total
        if (clickedExercise.progress >= clickedExercise.total) return

        clickedExercise.progress += 5
        val updatedProgress = "${clickedExercise.progress}/${clickedExercise.total}"
        view.progress_tv.text = updatedProgress
        val progress = clickedExercise.progress.toDouble()/clickedExercise.total.toDouble()
        animateProgressBar(view, progress)
    }

    private fun animateProgressBar(listItemView: View, progress: Double) {
        val doneAnim = ValueAnimator.ofInt(listItemView.progress_bar.measuredWidth, (progress * listItemView.bg_view.measuredWidth).toInt())
        doneAnim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = listItemView.progress_bar.layoutParams
            layoutParams.width = value
            listItemView.progress_bar.layoutParams = layoutParams
            listItemView.progress_bar.visibility = if (value <= 0) View.INVISIBLE else View.VISIBLE
        }
        doneAnim.duration = 150
        doneAnim.start()
    }

    private fun updateProgressBar(listItemView: View, progress: Double) {
        val layoutParams = listItemView.progress_bar.layoutParams
        layoutParams.width = (progress * listItemView.bg_view.measuredWidth).toInt()
        listItemView.progress_bar.layoutParams = layoutParams
        listItemView.progress_bar.visibility = if (progress <= 0) View.INVISIBLE else View.VISIBLE
    }
}