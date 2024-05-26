package com.example.dailynote.data.repository

import com.example.dailynote.data.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insert(note: Note)

    suspend fun update(note: Note)

    suspend fun delete(note: Note)

    fun getAllNotes(): Flow<List<Note>>

    fun getNotesById(id: Long): Flow<Note>
}