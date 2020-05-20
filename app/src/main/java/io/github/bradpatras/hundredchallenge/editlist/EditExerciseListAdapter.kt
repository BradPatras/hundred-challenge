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

class EditExerciseListAdapter(context: Context, exerciseRepository: ExerciseRepository): ListAdapter<Exercise, EditExerciseViewHolder>(ExerciseDiffCallback()) {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditExerciseViewHolder {
        val view = layoutInflater.inflate(R.layout.edit_exercise_list_item, parent, false)

        return EditExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.itemView.exercise_title_tv.editText?.setText(exercise.title)
        holder.itemView.exercise_rep_value_tv.editText?.setText(exercise.total.toString())
    }
}