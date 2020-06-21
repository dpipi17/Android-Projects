package com.example.todoapp.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note (
    var id: Int = 0,
    var title: String,
    var pinned: Boolean,
    var lastUpdateDate: Date,
    var subNotes: MutableList<SubNote>
) : Parcelable