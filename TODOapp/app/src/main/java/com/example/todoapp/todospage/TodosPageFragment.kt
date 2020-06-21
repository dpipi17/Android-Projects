package com.example.todoapp.todospage

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.dataclasses.Note
import com.example.todoapp.todospage.recyclerview.RecyclerViewAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class TodosPageFragment : Fragment(), TodosPageContract.View {

    private lateinit var progressBar: ProgressBar
    private var cells : MutableList<Note> = ArrayList()
    private lateinit var presenter: TodosPageContract.Presenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var takeNoteTextView: TextView
    private lateinit var navController: NavController
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var searchBar: EditText
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_todos_page, container, false)
        initFields(view)
        updateNotes("")
        addListeners()
        hideLoader()

        return view
    }

    private fun initFields(view: View) {
        activity?.applicationContext?.let {
            presenter = TodosPagePresenterImpl(this, it)
        }

        navController = findNavController()
        progressBar = view.findViewById(R.id.fragment_todos_page_progressBar)
        takeNoteTextView = view.findViewById(R.id.fragment_todos_page_take_note_tv)
        searchBar = view.findViewById(R.id.fragment_todos_page_search)
        initRecyclerView(view)
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = RecyclerViewAdapter(navController, this.context)
        var mLayoutManager = GridLayoutManager(activity, 2)
        mLayoutManager.setSpanSizeLookup(object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    RecyclerViewAdapter.SECTION_TITLE_TYPE -> 2
                    RecyclerViewAdapter.NOTE_CELL_TYPE -> 1
                    else -> 2
                }
            }
        })
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = adapter
    }

    private fun updateNotes(searchWord: String) {
        GlobalScope.launch {
            cells = presenter.getFilteredNotes(searchWord)

            (mContext as Activity).runOnUiThread {
                adapter.setData(cells)
            }
        }
    }

    private fun addListeners() {
        takeNoteTextView.setOnClickListener {
            val args = Bundle()
            args.putParcelable("note", Note(0, "", false, Date(), ArrayList()))
            navController.navigate(R.id.action_todosPageFragment_to_notePageFragment, args)
        }

        searchBar.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                updateNotes(searchBar.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun showLoader() {
        (mContext as Activity).runOnUiThread {
            progressBar?.visibility = View.VISIBLE
        }

    }

    override fun hideLoader() {
        (mContext as Activity).runOnUiThread {
            progressBar?.visibility = View.INVISIBLE
        }
    }
}