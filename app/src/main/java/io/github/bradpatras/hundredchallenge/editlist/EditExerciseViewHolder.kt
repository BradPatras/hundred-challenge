package io.github.bradpatras.hundredchallenge.editlist

import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.edit_exercise_list_item.view.*

class EditExerciseViewHolder(itemView: View, deleteButtonClickListener: ((EditExerciseViewHolder) -> Unit), titleChangeListener: ((String, EditExerciseViewHolder) -> Unit), repCountChangeListener: ((String, EditExerciseViewHolder) -> Unit)) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.exercise_title_et.doAfterTextChanged { titleChangeListener.invoke(it.toString(), this) }
        itemView.exercise_rep_value_et.doAfterTextChanged { repCountChangeListener.invoke(it.toString(), this) }
        itemView.delete_btn.setOnClickListener { deleteButtonClickListener.invoke(this) }
    }
}