package io.github.bradpatras.hundredchallenge.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.bradpatras.hundredchallenge.R
import io.github.bradpatras.hundredchallenge.data.Exercise
import io.github.bradpatras.hundredchallenge.data.ExerciseRepository
import io.github.bradpatras.hundredchallenge.list.ExerciseListAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var exerciseRepository: ExerciseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseRepository = ExerciseRepository()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ExerciseListAdapter(view.context, exerciseRepository)
        adapter.setHasStableIds(true)
        exerciseRecyclerView.layoutManager = LinearLayoutManager(view.context)
        exerciseRecyclerView.adapter = adapter

        exerciseRepository.getAllExercises().observe(viewLifecycleOwner, getExerciseListObserverForAdapter(adapter))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        view?.let {
            viewModel.timerController.linkToView(it.timerView)
        }
    }

    private fun getExerciseListObserverForAdapter(adapter: ExerciseListAdapter): Observer<List<Exercise>> {
        return Observer { exercises ->
            adapter.submitList(exercises)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                exerciseRepository.resetExercises()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

