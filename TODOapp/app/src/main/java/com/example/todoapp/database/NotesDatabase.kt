package com.example.todoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.database.note.Note
import com.example.todoapp.database.note.NoteDao
import com.example.todoapp.database.subnote.SubNote
import com.example.todoapp.database.subnote.SubNoteDao

@Database(entities = [Note::class, SubNote::class], version = 1)
@TypeConverters(Converters::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
    abstract fun getSubNoteDao(): SubNoteDao
}