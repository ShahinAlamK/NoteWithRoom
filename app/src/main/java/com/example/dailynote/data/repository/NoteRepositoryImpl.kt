package com.example.dailynote.data.repository

import com.example.dailynote.data.dao.NotesDao
import com.example.dailynote.data.models.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val notesDao: NotesDao) : NoteRepository {

    override suspend fun insert(note: Note) = notesDao.insert(note)

    override suspend fun update(note: Note) = notesDao.update(note)

    override suspend fun delete(note: Note) = notesDao.delete(note)

    override fun getAllNotes(): Flow<List<Note>> = notesDao.getAllNotes()

    override fun getNotesById(id: Long): Flow<Note> = notesDao.getNotesById(id)
}