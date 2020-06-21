package com.example.todoapp.database.note

import com.example.todoapp.database.subnote.SubNoteDao
import com.example.todoapp.database.subnote.SubNoteHelper
import com.example.todoapp.dataclasses.Note

class NoteHelper {

    companion object {

        fun toEntity(note: Note) : NoteEntity {
            return NoteEntity(note.id, note.title, note.pinned, note.lastUpdateDate)
        }

        fun fromEntity(entity: NoteEntity, subNoteDao: SubNoteDao) : Note {
            var subNoteEntities = subNoteDao.getSubNotesByNoteId(entity.id)
            var subNotes = SubNoteHelper.fromEntities(subNoteEntities)
            return Note(entity.id, entity.title, entity.pinned, entity.lastUpdateDate, subNotes)
        }

        fun toEntities(notes: MutableList<Note>) : MutableList<NoteEntity> {
            var entities : MutableList<NoteEntity> = ArrayList()
            notes.forEach {
                entities.add(toEntity(it))
            }
            return entities;
        }

        fun fromEntities(entities: MutableList<NoteEntity>, subNoteDao: SubNoteDao) : MutableList<Note> {
            var notes : MutableList<Note> = ArrayList()
            entities.forEach {
                notes.add(fromEntity(it, subNoteDao))
            }
            return notes
        }

    }
}