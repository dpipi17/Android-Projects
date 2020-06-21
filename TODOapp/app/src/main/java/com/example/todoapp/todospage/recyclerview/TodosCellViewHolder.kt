package com.example.todoapp.todospage.recyclerview

import android.content.Context
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.customviews.checkededittext.CheckedEditTextModel
import com.example.todoapp.dataclasses.NoteWithSubNotes

class TodosCellViewHolder(itemView: View, var context: Context?) : RecyclerView.ViewHolder(itemView) {

    companion object{
        const val MAX_SUB_NOTE_ITEMS_NUM = 3
    }

    private var title : TextView? = null
    private var subNotes: LinearLayout? = null
    private var unCheckedItemsTextView: TextView? = null
    private var checkedItemsTextView: TextView? = null

    init {
        title = itemView.findViewById(R.id.todos_cell_title)
        subNotes = itemView.findViewById(R.id.todos_cell_linear_layout)
        unCheckedItemsTextView = itemView.findViewById(R.id.todos_cell_unchecked_items)
        checkedItemsTextView = itemView.findViewById(R.id.todos_cell_checked_items)
    }

    fun setUpView(model : NoteWithSubNotes) {
        title?.text = model.note.title

        var printedSubNoteNum = 0
        var checkedItemsNum = 0
        var uncheckedItemsNum = 0
        model.subNotes.forEach {
            if (it.checked) checkedItemsNum++ else uncheckedItemsNum++

        }

        subNotes?.removeAllViews()
        model.subNotes.forEach {
            if (!it.checked) {
                if (printedSubNoteNum < MAX_SUB_NOTE_ITEMS_NUM) {
                    addSubNoteItem(
                        CheckedEditTextModel(
                            it.id,
                            false,
                            it.description
                        )
                    )
                    printedSubNoteNum++
                    uncheckedItemsNum--
                }
            }
        }

        model.subNotes.forEach {
            if (it.checked) {
                if (printedSubNoteNum < MAX_SUB_NOTE_ITEMS_NUM) {
                    addSubNoteItem(
                        CheckedEditTextModel(
                            it.id,
                            true,
                            it.description
                        )
                    )
                    printedSubNoteNum++
                    checkedItemsNum--
                }
            }
        }
        subNotes?.addView(TextView(context))


        if (uncheckedItemsNum > 0) {
            val items = if (uncheckedItemsNum == 1) "item" else "items"
            unCheckedItemsTextView?.text = "+${uncheckedItemsNum} unchecked ${items}"
        } else {
            unCheckedItemsTextView?.visibility = View.GONE
        }

        if (checkedItemsNum > 0) {
            val items = if (checkedItemsNum == 1) "item" else "items"
            checkedItemsTextView?.text = "+${checkedItemsNum} checked ${items}"
        } else {
            checkedItemsTextView?.visibility = View.GONE
        }

    }

    private fun addSubNoteItem(model: CheckedEditTextModel) {
        var subNoteItem = CheckBox(context)
        subNoteItem.isChecked = model.checked
        subNoteItem.text = model.description
        subNoteItem.isClickable = false
        subNotes?.addView(subNoteItem)
    }
}