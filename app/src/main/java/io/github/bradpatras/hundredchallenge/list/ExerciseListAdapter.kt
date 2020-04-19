package io.github.bradpatras.hundredchallenge.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.bradpatras.hundredchallenge.R
import io.github.bradpatras.hundredchallenge.data.Exercise
import kotlinx.android.synthetic.main.exercise_list_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseListAdapter(context: Context): RecyclerView.Adapter<ExerciseViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val uiCoroutineScope = CoroutineScope(Dispatchers.Main)

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
        holder.itemView.setOnClickListener { _ ->
            onItemClicked(position, holder)
        }
        holder.itemView.setOnLongClickListener { _ ->
            onItemLongClicked(position, holder)
            return@setOnLongClickListener true
        }

        val progress = exercise.progress.toDouble()/exercise.total.toDouble()
        if (progress > 0) {
            uiCoroutineScope.launch {
                holder.updateProgressBar(progress)
            }
        }
    }

    private fun onItemLongClicked(position: Int, holder: ExerciseViewHolder) {
        val clickedExercise = exerciseList.getOrNull(position) ?: return

        // Do nothing if progress is already 0
        if (clickedExercise.progress == 0) return
        val oldProgress = clickedExercise.progress.toDouble()/clickedExercise.total.toDouble()
        clickedExercise.progress -= 5

        val updatedProgress = "${clickedExercise.progress}/${clickedExercise.total}"
        holder.view.progress_tv.text = updatedProgress
        val progress = clickedExercise.progress.toDouble()/clickedExercise.total.toDouble()
        holder.animateProgressBar(oldProgress, progress)
    }

    private fun onItemClicked(position: Int, holder: ExerciseViewHolder) {
        val clickedExercise = exerciseList.getOrNull(position) ?: return

        // Do nothing if progress is already equal to the total
        if (clickedExercise.progress >= clickedExercise.total) return
        val oldProgress = clickedExercise.progress.toDouble()/clickedExercise.total.toDouble()
        clickedExercise.progress += 5
        val updatedProgress = "${clickedExercise.progress}/${clickedExercise.total}"
        holder.view.progress_tv.text = updatedProgress
        val progress = clickedExercise.progress.toDouble()/clickedExercise.total.toDouble()
        holder.animateProgressBar(oldProgress, progress)
    }
}