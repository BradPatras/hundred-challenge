package io.github.bradpatras.hundredchallenge.editlist

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import io.github.bradpatras.hundredchallenge.data.Exercise
import io.github.bradpatras.hundredchallenge.mainlist.ExerciseDiffCallback

class EditExerciseListAdapter: ListAdapter<Exercise, EditExerciseViewHolder>(ExerciseDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditExerciseViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: EditExerciseViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}