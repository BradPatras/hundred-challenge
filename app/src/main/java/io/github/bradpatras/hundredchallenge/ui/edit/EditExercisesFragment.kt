package io.github.bradpatras.hundredchallenge.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.github.bradpatras.hundredchallenge.R
import io.github.bradpatras.hundredchallenge.data.ExerciseRepository

class EditExercisesFragment : Fragment() {
    private lateinit var viewModel: EditExercisesViewModel
    private val exerciseRepository: ExerciseRepository = ExerciseRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_exercises, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditExercisesViewModel::class.java)
    }
}
