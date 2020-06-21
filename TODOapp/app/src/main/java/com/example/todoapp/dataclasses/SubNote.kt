package com.example.todoapp.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubNote(
    var id: Int = 0,
    var noteId: Int,
    var description: String,
    var checked: Boolean
) : Parcelable