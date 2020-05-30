package io.github.bradpatras.hundredchallenge.ui.add

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.github.bradpatras.hundredchallenge.R
import io.github.bradpatras.hundredchallenge.data.Exercise
import io.github.bradpatras.hundredchallenge.data.ExerciseRepository
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_add_exercise_dialog.view.*
import java.util.*

class AddExerciseDialogFragment : DialogFragment() {
    private lateinit var repository: ExerciseRepository
    private lateinit var viewModel: AddExerciseViewModel

    private var titleTextLiveData: MutableLiveData<String> = MutableLiveData()
    private var repCountLiveData: MutableLiveData<Int> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = ExerciseRepository()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        return inflater.inflate(R.layout.fragment_add_exercise_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.cancel_button.setOnClickListener(this::cancelButtonClicked)
        view.create_button.setOnClickListener(this::createButtonClicked)
        view.exercise_title_tl.editText?.doAfterTextChanged(this::titleEditTextDidEdit)
        view.exercise_rep_value_tl.editText?.doAfterTextChanged(this::repCountEditTextDidEdit)
    }

    private fun cancelButtonClicked(button: View) {
        this.dismiss()
    }

    private fun createButtonClicked(button: View) {
        val title = viewModel.title ?: "Untitled"
        val repCount = viewModel.repCount ?: 100
        val exercise = Exercise(UUID.randomUUID().leastSignificantBits, title, 0, repCount)
        compositeDisposable.add(
            repository.insertExercises(listOf(exercise)).subscribe { _ ->
                this.dismiss()
            })
    }

    private fun titleEditTextDidEdit(text: Editable?) {
        titleTextLiveData.postValue(text.toString())
    }

    private fun repCountEditTextDidEdit(text: Editable?) {
        repCountLiveData.postValue(text.toString().toIntOrNull())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AddExerciseViewModel::class.java)

        titleTextLiveData.observe(this.viewLifecycleOwner, Observer { newTitle ->
            viewModel.title = newTitle
        })
        repCountLiveData.observe(this.viewLifecycleOwner, Observer { newRepCount ->
            viewModel.repCount = newRepCount
        })
    }
}
