package io.github.bradpatras.hundredchallenge.ui.main

import android.content.Context
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
import it.sephiroth.android.library.xtooltip.ClosePolicy
import it.sephiroth.android.library.xtooltip.Tooltip
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*

private const val TOOLTIP_SHOWN_KEY: String = "tooltipShownKey"

class MainFragment : Fragment(R.layout.main_fragment) {

    private lateinit var viewModel: MainViewModel
    private lateinit var exerciseRepository: ExerciseRepository

    private var tooltips: MutableList<Tooltip> = mutableListOf()

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

        showTooltipsIfNeeded(view)
    }

    private fun showTooltipsIfNeeded(fragmentView: View) {
        fun showRecyclerViewTooltip() {
            val exerciseTooltip = Tooltip.Builder(fragmentView.context)
                .anchor(exerciseRecyclerView, 0, 0, true)
                .text(fragmentView.context.getString(R.string.exercise_tooltip))
                .arrow(true)
                .floatingAnimation(null)
                .styleId(R.style.ToolTipStyle)
                .closePolicy(ClosePolicy.TOUCH_ANYWHERE_CONSUME)
                .create()

            exerciseTooltip.show(exerciseRecyclerView, Tooltip.Gravity.TOP, false)
            tooltips.add(exerciseTooltip)
        }

        fun showTimerViewTooltip() {
            val timerTooltip = Tooltip.Builder(fragmentView.context)
                .anchor(timerView, 0, 0, true)
                .activateDelay(500)
                .text(fragmentView.context.getString(R.string.timer_tooltip))
                .floatingAnimation(null)
                .styleId(R.style.ToolTipStyle)
                .arrow(true)
                .closePolicy(ClosePolicy.TOUCH_ANYWHERE_CONSUME)
                .create()
            timerTooltip.doOnHidden { showRecyclerViewTooltip() }
            timerTooltip.show(timerView, Tooltip.Gravity.BOTTOM, false)

            tooltips.add(timerTooltip)
        }

        // Check for the tooltips flag, if the flag doesn't exist then it's the first time
        // the app has been opened or since the app data has been cleared and we need to show
        // the tooltips
        val prefs = requireContext().getSharedPreferences(requireContext().getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        if (!prefs.contains(TOOLTIP_SHOWN_KEY)) {
            timerView.post { showTimerViewTooltip() }
            prefs.edit()
                .putBoolean(TOOLTIP_SHOWN_KEY, true)
                .apply()
        }
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
                viewModel.timerController.resetTimer()
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

