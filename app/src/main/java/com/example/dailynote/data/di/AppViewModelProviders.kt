package com.example.dailynote.data.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dailynote.BaseApplication
import com.example.dailynote.data.viewmodels.AddViewModel
import com.example.dailynote.data.viewmodels.HomeViewModel

object AppViewModelProviders {
    val fectory = viewModelFactory {
        initializer {
            HomeViewModel(applicationKey().noteContainer.noteRepository)
        }
        initializer {
            AddViewModel(applicationKey().noteContainer.noteRepository)
        }
    }
}

fun CreationExtras.applicationKey(): BaseApplication {
    return (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BaseApplication)
}