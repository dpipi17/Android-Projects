package com.example.todoapp.todospage.recyclerview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.note.Note
import com.example.todoapp.dataclasses.NoteWithSubNotes
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewAdapter(val navController: NavController, val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val SECTION_TITLE_TYPE = 1
        const val NOTE_CELL_TYPE = 2
        const val SECTION_TITLE_TYPE_NOTE_ID = -10
    }

    private var cells: MutableList<NoteWithSubNotes> = ArrayList()

    private var clickListener = View.OnClickListener {
        val args = Bundle()
        args.putParcelable("noteWithSubNotes", cells[it.tag as Int])
        navController.navigate(R.id.action_todosPageFragment_to_notePageFragment, args)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cellView: View
        if (viewType == NOTE_CELL_TYPE) {
            cellView = LayoutInflater.from(parent.context).inflate(R.layout.todos_cell, parent, false)
            cellView.setOnClickListener(clickListener)
            return TodosCellViewHolder(cellView, context)
        } else {
            cellView = LayoutInflater.from(parent.context).inflate(R.layout.section_cell, parent, false)
            return SectionCellViewHolder(cellView)
        }
    }

    override fun getItemCount(): Int {
        return cells.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TodosCellViewHolder) {
            holder.setUpView(cells[position])
            holder.itemView.tag = holder.adapterPosition
        } else if (holder is SectionCellViewHolder) {
            holder.setUpView(cells[position].note.title)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (cells[position].note.id == SECTION_TITLE_TYPE_NOTE_ID) {
            return SECTION_TITLE_TYPE
        }
        return NOTE_CELL_TYPE
    }

    fun setData(noteWithSubNotes: MutableList<NoteWithSubNotes>) {
        cells.clear()
        var pinnedNoteFound = false

        noteWithSubNotes.forEach {
            if (it.note.pinned) {
                if (!pinnedNoteFound) {
                    cells.add(NoteWithSubNotes(Note( SECTION_TITLE_TYPE_NOTE_ID, "PINNED", false, Date()), ArrayList()))
                }
                cells.add(it)
                pinnedNoteFound = true
            }
        }

        var unPinnedNoteFound = false
        noteWithSubNotes.forEach {
            if (!it.note.pinned) {
                if (pinnedNoteFound && !unPinnedNoteFound) {
                    cells.add(NoteWithSubNotes(Note(SECTION_TITLE_TYPE_NOTE_ID, "OTHERS", false, Date()), ArrayList()))
                }
                cells.add(it)
                unPinnedNoteFound = true
            }
        }

        notifyDataSetChanged()
    }
}