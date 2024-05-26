package com.example.dailynote.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailynote.data.models.Note

@Database(
    entities = [Note::class],
    version = 1
)

abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao


    companion object {
        @Volatile
        private var Instance: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase{
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    NotesDatabase::class.java,
                    name = "note_db"
                ).build().also {
                    Instance = it
                }
            }
        }

    }
}