package com.example.dailynote.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dailynote.data.models.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * from notes ORDER BY time DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * from notes WHERE id = :id")
    fun getNotesById(id: Long): Flow<Note>
}