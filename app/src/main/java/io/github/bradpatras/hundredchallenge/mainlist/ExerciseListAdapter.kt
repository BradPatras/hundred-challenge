package io.github.bradpatras.hundredchallenge.mainlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.github.bradpatras.hundredchallenge.R
import io.github.bradpatras.hundredchallenge.data.Exercise
import io.github.bradpatras.hundredchallenge.data.ExerciseRepository
import kotlinx.android.synthetic.main.exercise_list_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        val isProgressEqual = oldItem.progress == newItem.progress
        val isTotalEqual = oldItem.total == newItem.total
        val isTitleEqual = oldItem.title == newItem.title
        return isProgressEqual and isTotalEqual and isTitleEqual
    }
}

class ExerciseListAdapter(context: Context, val repository: ExerciseRepository): ListAdapter<Exercise, ExerciseViewHolder>(ExerciseDiffCallback()) {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val uiCoroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = layoutInflater.inflate(R.layout.exercise_list_item, parent, false)

        return ExerciseViewHolder((view), this::onItemClicked, this::onItemLongClicked)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        val progressText = "${exercise.progress}/${exercise.total}"
        holder.itemView.progress_tv.text = progressText
        holder.itemView.exercise_tv.text = exercise.title

        val progress = exercise.progress.toDouble() / exercise.total.toDouble()
        
        uiCoroutineScope.launch {
            holder.updateProgressBar(progress)
        }
    }

    private fun onItemLongClicked(holder: ExerciseViewHolder) {
        val position = holder.adapterPosition
        val clickedExercise = getItem(position)

        // Do nothing if progress is already 0
        if (clickedExercise.progress == 0) return
        val oldProgress = clickedExercise.progress.toDouble()/clickedExercise.total.toDouble()
        clickedExercise.progress -= 5

        val updatedProgress = "${clickedExercise.progress}/${clickedExercise.total}"
        holder.view.progress_tv.text = updatedProgress
        val progress = clickedExercise.progress.toDouble()/clickedExercise.total.toDouble()
        holder.animateProgressBar(oldProgress, progress)

        repository.updateExercises(listOf(clickedExercise))
            .subscribe()
    }

    private fun onItemClicked(holder: ExerciseViewHolder) {
        val position = holder.adapterPosition
        val clickedExercise = getItem(position)

        // Do nothing if progress is already equal to the total
        if (clickedExercise.progress >= clickedExercise.total) return
        val oldProgress = clickedExercise.progress.toDouble()/clickedExercise.total.toDouble()
        clickedExercise.progress += 5
        val updatedProgress = "${clickedExercise.progress}/${clickedExercise.total}"
        holder.view.progress_tv.text = updatedProgress
        val progress = clickedExercise.progress.toDouble()/clickedExercise.total.toDouble()
        holder.animateProgressBar(oldProgress, progress)

        repository.updateExercises(listOf(clickedExercise))
            .subscribe()
    }
}