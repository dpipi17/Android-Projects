package com.example.todoapp.customviews.checkededittext

import android.content.Context
import android.widget.CheckBox
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.todoapp.R

class CheckedEditText(context: Context?) : ConstraintLayout(context) {

    private var checkBox: CheckBox? = null
    private var descriptionEditText: EditText? = null
    private var subNoteId: Int = 0

    init {
        inflate(context, R.layout.custom_checked_edit_text, this)
        checkBox = findViewById(R.id.custom_checked_edit_text_checkbox)
        descriptionEditText = findViewById(R.id.custom_checked_edit_text_description)
    }

    fun setUpView(model: CheckedEditTextModel, checkedEditTextClick: CheckedEditTextClick? = null) {
        subNoteId = model.subNoteId
        checkBox?.isChecked = model.checked
        descriptionEditText?.setText(model.description)

        enable(!model.checked)
        checkBox?.setOnClickListener {
            checkedEditTextClick?.onClick(this, checkBox?.isChecked!!)
        }
        requestLayout()
    }

    fun isChecked() : Boolean {
        return checkBox!!.isChecked
    }

    fun getDescription() : String {
        return descriptionEditText?.text.toString()
    }

    fun getSubNoteId() : Int {
        return subNoteId
    }

    fun enable(value: Boolean) {
        descriptionEditText?.isEnabled = value
    }

    interface CheckedEditTextClick {
        fun onClick(checkedEditText: CheckedEditText, newValue: Boolean)
    }

}
