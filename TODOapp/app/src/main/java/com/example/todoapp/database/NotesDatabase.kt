package com.example.todoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.database.note.NoteDao
import com.example.todoapp.database.note.NoteEntity
import com.example.todoapp.database.subnote.SubNoteDao
import com.example.todoapp.database.subnote.SubNoteEntity

@Database(entities = [NoteEntity::class, SubNoteEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
    abstract fun getSubNoteDao(): SubNoteDao
}