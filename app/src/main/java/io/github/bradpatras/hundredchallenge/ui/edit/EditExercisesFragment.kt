package io.github.bradpatras.hundredchallenge.ui.edit

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
import io.github.bradpatras.hundredchallenge.editlist.EditExerciseListAdapter
import io.github.bradpatras.hundredchallenge.ui.add.AddExerciseDialogFragment
import kotlinx.android.synthetic.main.fragment_edit_exercises.*

class EditExercisesFragment : Fragment(R.layout.fragment_edit_exercises) {
    private val exerciseRepository: ExerciseRepository = ExerciseRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edit_list_action_bar.setNavigationOnClickListener { onNavigationItemClicked() }
        edit_list_action_bar.setOnMenuItemClickListener { onOptionsItemSelected(it) }

        val adapter = EditExerciseListAdapter(view.context, exerciseRepository)
        adapter.setHasStableIds(true)
        editRecyclerView.layoutManager = LinearLayoutManager(view.context)
        editRecyclerView.adapter = adapter

        exerciseRepository.getAllExercises().observe(viewLifecycleOwner, getExerciseListObserverForAdapter(adapter))
    }

    private fun getExerciseListObserverForAdapter(adapter: EditExerciseListAdapter): Observer<List<Exercise>> {
        return Observer { exercises ->
            adapter.submitList(exercises)
        }
    }

    private fun onNavigationItemClicked() {
        activity?.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                AddExerciseDialogFragment()
                    .show(parentFragmentManager, "AddExercise")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
