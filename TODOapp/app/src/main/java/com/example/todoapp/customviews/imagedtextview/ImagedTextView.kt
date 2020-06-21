package com.example.todoapp.customviews.imagedtextview

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.todoapp.R

class ImagedTextView (context: Context?) : ConstraintLayout(context) {

    private var image: ImageView? = null
    private var text: TextView? = null

    init {
        inflate(context, R.layout.custom_imaged_textview, this)
        image = findViewById(R.id.custom_imaged_textview_image)
        text = findViewById(R.id.custom_imaged_textview_textview)
    }

    fun setUpView(model: ImagedTextViewModel) {
        image?.setImageResource(model.resId)
        text?.text = model.text
    }

    fun setText(newText: String) {
        text?.text = newText
    }
}