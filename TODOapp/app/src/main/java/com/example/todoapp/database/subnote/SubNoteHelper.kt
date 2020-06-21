package com.example.todoapp.database.subnote

import com.example.todoapp.dataclasses.SubNote

class SubNoteHelper {

    companion object {

        fun toEntity(subNote: SubNote) : SubNoteEntity {
            return SubNoteEntity(subNote.id, subNote.noteId, subNote.description, subNote.checked)
        }

        fun fromEntity(entity: SubNoteEntity) : SubNote {
            return SubNote(entity.id, entity.noteId, entity.description, entity.checked)
        }

        fun toEntities(subNotes: MutableList<SubNote>) : MutableList<SubNoteEntity> {
            var entities : MutableList<SubNoteEntity> = ArrayList()
            subNotes.forEach {
                entities.add(toEntity(it))
            }
            return entities;
        }

        fun fromEntities(entities: MutableList<SubNoteEntity>) : MutableList<SubNote> {
            var subNotes : MutableList<SubNote> = ArrayList()
            entities.forEach {
                subNotes.add(fromEntity(it))
            }
            return subNotes
        }

    }
}