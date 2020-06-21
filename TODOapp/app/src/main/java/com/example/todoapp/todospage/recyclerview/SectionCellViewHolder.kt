package com.example.todoapp.todospage.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class SectionCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var titleTextView: TextView? = null

    init {
        titleTextView = itemView.findViewById(R.id.section_title)
    }

    fun setUpView(title : String) {
        titleTextView?.text = title
    }
}