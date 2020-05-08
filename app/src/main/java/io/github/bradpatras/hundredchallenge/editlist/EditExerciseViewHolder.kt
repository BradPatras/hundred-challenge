package io.github.bradpatras.hundredchallenge.editlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.edit_exercise_list_item.view.*

class EditExerciseViewHolder(itemView: View,
                             editClickListener: ((EditExerciseViewHolder) -> Unit)
) : RecyclerView.ViewHolder(itemView) {

    init {
       itemView.edit_btn.setOnClickListener { editClickListener.invoke(this) }
    }
}