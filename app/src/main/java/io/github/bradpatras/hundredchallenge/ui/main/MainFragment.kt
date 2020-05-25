package io.github.bradpatras.hundredchallenge.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.bradpatras.hundredchallenge.R
import io.github.bradpatras.hundredchallenge.data.Exercise
import io.github.bradpatras.hundredchallenge.data.ExerciseRepository
import io.github.bradpatras.hundredchallenge.mainlist.ExerciseListAdapter
import io.github.bradpatras.hundredchallenge.ui.edit.EditExercisesFragment
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment(R.layout.main_fragment) {

    private lateinit var viewModel: MainViewModel
    private lateinit var exerciseRepository: ExerciseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseRepository = ExerciseRepository()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main_action_bar.setOnMenuItemClickListener { onOptionsItemSelected(it) }
        val adapter = ExerciseListAdapter(view.context, exerciseRepository)
        exerciseRecyclerView.layoutManager = LinearLayoutManager(view.context)
        exerciseRecyclerView.adapter = adapter

        exerciseRepository.getAllExercises().observe(viewLifecycleOwner, getExerciseListObserverForAdapter(adapter))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        view?.let {
            viewModel.timerController.linkToView(it.timerView)
        }
    }

    private fun getExerciseListObserverForAdapter(adapter: ExerciseListAdapter): Observer<List<Exercise>> {
        return Observer { exercises ->
            adapter.submitList(exercises)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                exerciseRepository.resetExercises()
                return true
            }
            R.id.action_edit_exercises -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, EditExercisesFragment())
                    .addToBackStack(null)
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .commit()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

