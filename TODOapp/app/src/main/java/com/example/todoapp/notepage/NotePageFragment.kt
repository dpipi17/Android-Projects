package com.example.todoapp.notepage

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.customviews.checkededittext.CheckedEditText
import com.example.todoapp.customviews.checkededittext.CheckedEditTextModel
import com.example.todoapp.customviews.imagedtextview.ImagedTextView
import com.example.todoapp.customviews.imagedtextview.ImagedTextViewModel
import com.example.todoapp.database.subnote.SubNote
import com.example.todoapp.dataclasses.NoteWithSubNotes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class NotePageFragment : Fragment(), NotePageContract.View, CheckedEditText.CheckedEditTextClick {

    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: NotePageContract.Presenter
    private lateinit var noteWithSubNotes : NoteWithSubNotes
    private lateinit var titleEditText : EditText
    private lateinit var lastUpdateTimeTextView : TextView
    private lateinit var pinnedImage : ImageView
    private lateinit var backImage : ImageView
    private lateinit var subNotesList : LinearLayout
    private lateinit var addNoteImagedTextView: ImagedTextView
    private lateinit var checkedItemsImagedTextView: ImagedTextView
    private lateinit var mContext: Context

    private var lastUncheckedIndex: Int = 1
    private var checkedItemsNum: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_page, container, false)

        noteWithSubNotes = arguments?.get("noteWithSubNotes") as NoteWithSubNotes
        initFields(view)
        setFieldsValues(noteWithSubNotes)
        addClickListeners()
        hideLoader()

        return view
    }

    private fun initFields(view: View) {
        activity?.applicationContext?.let {
            presenter = NotePagePresenterImpl(this, it)
        }

        titleEditText = EditText(context)
        titleEditText.setBackgroundResource(android.R.color.transparent);
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(40, 0, 0, 20)
        titleEditText.layoutParams = params
        titleEditText.textSize = 30.0F
        titleEditText.hint = "Title"
        titleEditText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

        progressBar = view.findViewById(R.id.fragment_note_page_progressBar)
        lastUpdateTimeTextView = view.findViewById(R.id.fragment_note_page_last_update_time)
        pinnedImage = view.findViewById(R.id.pinnedImage)
        backImage = view.findViewById(R.id.back)
        subNotesList = view.findViewById(R.id.fragment_note_page_sub_notes_lists)

        addNoteImagedTextView = ImagedTextView(context)
        checkedItemsImagedTextView = ImagedTextView(context)
    }

    private fun setFieldsValues(noteWithSubNotes: NoteWithSubNotes) {
        pinnedImage.setImageResource(if (noteWithSubNotes.note.pinned) R.drawable.pinned else R.drawable.unpinned)
        lastUpdateTimeTextView.text = "Edited ${SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(noteWithSubNotes.note.lastUpdateDate)}"

        titleEditText.setText(noteWithSubNotes.note.title)
        subNotesList.addView(titleEditText)

        noteWithSubNotes.subNotes.forEach {
            if (!it.checked) {
                lastUncheckedIndex++
                addSubNote(
                    CheckedEditTextModel(
                        it.id,
                        false,
                        it.description
                    )
                )
            } else {
                checkedItemsNum++
            }
        }


        addImagedTextView(ImagedTextViewModel(R.drawable.plus, "List item"), addNoteImagedTextView)
        addSeparator()
        addImagedTextView(ImagedTextViewModel(R.drawable.downarrow, "+${checkedItemsNum} Checked ${if (checkedItemsNum == 1) "item" else "items"}"), checkedItemsImagedTextView)

        noteWithSubNotes.subNotes.forEach {
            if (it.checked) {
                addSubNote(
                    CheckedEditTextModel(
                        it.id,
                        true,
                        it.description
                    )
                )
            }
        }
    }

    private fun addSubNote(model: CheckedEditTextModel, index: Int = -1) {
        var subNoteItem =
            CheckedEditText(
                context
            )
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(60, 0, 0, 0)
        subNoteItem.layoutParams = params
        subNoteItem.setUpView(model, this)
        
        if (index == -1) {
            subNotesList.addView(subNoteItem)
        } else {
            subNotesList.addView(subNoteItem, index)
        }
    }

    private fun addImagedTextView(model: ImagedTextViewModel, imagedTextView: ImagedTextView) {
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(60, 0, 0, 0)
        imagedTextView.layoutParams = params
        imagedTextView.setUpView(model)
        subNotesList.addView(imagedTextView)
    }

    private fun addSeparator() {
        val separator = ImageView(context)
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2)
        params.setMargins(0, 20, 0, 20)
        separator.layoutParams = params
        separator.setBackgroundColor(Color.GRAY)
        subNotesList.addView(separator)
    }

    private fun addClickListeners() {
        pinnedImage.setOnClickListener {
            noteWithSubNotes.note.pinned = !noteWithSubNotes.note.pinned
            pinnedImage.setImageResource(if (noteWithSubNotes.note.pinned) R.drawable.pinned else R.drawable.unpinned)
        }

        backImage.setOnClickListener {
            GlobalScope.launch {
                presenter.saveNote(getNoteToSave())

                (mContext as Activity).runOnUiThread {
                    findNavController().navigate(R.id.action_notePageFragment_to_todosPageFragment)
                }
            }
        }

        addNoteImagedTextView.setOnClickListener {
            addSubNote(CheckedEditTextModel(0, false, ""), lastUncheckedIndex++)
        }
    }

    private fun getNoteToSave() : NoteWithSubNotes {
        noteWithSubNotes.note.title = titleEditText.text.toString()

        noteWithSubNotes.subNotes.clear()
        for (i in 0 until subNotesList.childCount) {
            val v: View = subNotesList.getChildAt(i)

            if (v is CheckedEditText) {
                noteWithSubNotes.subNotes.add(SubNote(v.getSubNoteId(), noteWithSubNotes.note.id, v.getDescription(), v.isChecked()))
            }
        }

        return noteWithSubNotes
    }

    private fun updateCheckedSubNotesTextView() {
        checkedItemsImagedTextView.setText("+${checkedItemsNum} Checked ${if (checkedItemsNum == 1) "item" else "items"}")
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

    override fun onClick(checkedEditText: CheckedEditText, newValue: Boolean) {
        subNotesList.removeView(checkedEditText)
        if (newValue) {
            checkedItemsNum++
            lastUncheckedIndex--
            subNotesList.addView(checkedEditText)
        } else {
            checkedItemsNum--
            subNotesList.addView(checkedEditText, lastUncheckedIndex)
            lastUncheckedIndex++
        }
        checkedEditText.enable(!newValue)
        updateCheckedSubNotesTextView()
    }
}