package io.github.bradpatras.hundredchallenge.ui.add

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import io.github.bradpatras.hundredchallenge.R
import io.github.bradpatras.hundredchallenge.data.ExerciseRepository
import kotlinx.android.synthetic.main.fragment_add_exercise_dialog.view.*

class AddExerciseDialogFragment : DialogFragment() {
    lateinit var repository: ExerciseRepository
    lateinit var viewModel: AddExerciseViewModel

    lateinit var titleTextLiveData: MutableLiveData<String>
    lateinit var repCountLiveData: MutableLiveData<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = ExerciseRepository()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    }

    private fun titleEditTextDidEdit(text: Editable?) {
        titleTextLiveData.postValue(text.toString())
    }

    private fun repCountEditTextDidEdit(text: Editable?) {
        repCountLiveData.postValue(text.toString().toInt())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AddExerciseViewModel::class.java)
    }

}
