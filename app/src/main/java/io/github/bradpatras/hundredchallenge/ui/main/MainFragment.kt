package io.github.bradpatras.hundredchallenge.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.bradpatras.hundredchallenge.R
import io.github.bradpatras.hundredchallenge.data.ExerciseRepository
import io.github.bradpatras.hundredchallenge.list.ExerciseListAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var timerController: TimerController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timerController = TimerController(view.timerView)
        val adapter = ExerciseListAdapter(view.context)
        adapter.exerciseList = ExerciseRepository().getAllExercises()
        adapter.setHasStableIds(true)
        adapter.notifyDataSetChanged()
        exerciseRecyclerView.layoutManager = LinearLayoutManager(view.context)
        exerciseRecyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }
}

