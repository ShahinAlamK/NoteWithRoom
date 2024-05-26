package com.example.dailynote.data.di

import android.content.Context
import com.example.dailynote.data.dao.NotesDatabase
import com.example.dailynote.data.repository.NoteRepository
import com.example.dailynote.data.repository.NoteRepositoryImpl

interface NoteContainer {

    val noteRepository: NoteRepository
}

class DefaultNoteContainer(context: Context) : NoteContainer {

    override val noteRepository: NoteRepository by lazy {
        NoteRepositoryImpl(NotesDatabase.getDatabase(context).notesDao())
    }

}