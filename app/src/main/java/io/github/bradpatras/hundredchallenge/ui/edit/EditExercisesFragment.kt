package io.github.bradpatras.hundredchallenge.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.bradpatras.hundredchallenge.R
import io.github.bradpatras.hundredchallenge.data.Exercise
import io.github.bradpatras.hundredchallenge.data.ExerciseRepository
import io.github.bradpatras.hundredchallenge.editlist.EditExerciseListAdapter
import kotlinx.android.synthetic.main.fragment_edit_exercises.*

class EditExercisesFragment : Fragment() {
    private lateinit var viewModel: EditExercisesViewModel
    private val exerciseRepository: ExerciseRepository = ExerciseRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edit_list_action_bar.setNavigationOnClickListener { onNavigationItemClicked() }

        val adapter = EditExerciseListAdapter(view.context, exerciseRepository)
        adapter.setHasStableIds(true)
        editRecyclerView.layoutManager = LinearLayoutManager(view.context)
        editRecyclerView.adapter = adapter

        exerciseRepository.getAllExercises().observe(viewLifecycleOwner, getExerciseListObserverForAdapter(adapter))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_exercises, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditExercisesViewModel::class.java)
    }

    private fun getExerciseListObserverForAdapter(adapter: EditExerciseListAdapter): Observer<List<Exercise>> {
        return Observer { exercises ->
            adapter.submitList(exercises)
        }
    }

    private fun onNavigationItemClicked() {
        activity?.onBackPressed()
    }
}
