package com.example.dailynote

import android.app.Application
import com.example.dailynote.data.di.DefaultNoteContainer
import com.example.dailynote.data.di.NoteContainer

class BaseApplication : Application() {

    lateinit var noteContainer: NoteContainer
    override fun onCreate() {
        super.onCreate()
        noteContainer = DefaultNoteContainer(this)
    }
}