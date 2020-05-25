package io.github.bradpatras.hundredchallenge.editlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import io.github.bradpatras.hundredchallenge.R
import io.github.bradpatras.hundredchallenge.data.Exercise
import io.github.bradpatras.hundredchallenge.data.ExerciseRepository
import io.github.bradpatras.hundredchallenge.mainlist.ExerciseDiffCallback
import kotlinx.android.synthetic.main.edit_exercise_list_item.view.*

class EditExerciseListAdapter(context: Context, private val exerciseRepository: ExerciseRepository): ListAdapter<Exercise, EditExerciseViewHolder>(ExerciseDiffCallback()) {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditExerciseViewHolder {
        val view = layoutInflater.inflate(R.layout.edit_exercise_list_item, parent, false)

        return EditExerciseViewHolder(view, this::deleteButtonClicked, this::titleEditTextDidChange, this::repCountEditTextDidChange)
    }

    override fun onBindViewHolder(holder: EditExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.itemView.exercise_title_tl.editText?.setText(exercise.title)
        holder.itemView.exercise_rep_value_tl.editText?.setText(exercise.total.toString())
    }

    private fun titleEditTextDidChange(newTitle: String, viewHolder: EditExerciseViewHolder) {
        val exercise = getItem(viewHolder.adapterPosition)
        exercise.title = newTitle
        exerciseRepository.updateExercises(listOf(exercise)).subscribe()
    }

    private fun repCountEditTextDidChange(newRepCount: String, viewHolder: EditExerciseViewHolder) {
        val exercise = getItem(viewHolder.adapterPosition)
        exercise.total = newRepCount.toIntOrNull() ?: 0
        exerciseRepository.updateExercises(listOf(exercise)).subscribe()
    }

    private fun deleteButtonClicked(viewHolder: EditExerciseViewHolder) {
        val exercise = getItem(viewHolder.adapterPosition)
        exerciseRepository.deleteExercises(listOf(exercise)).subscribe()
    }
}