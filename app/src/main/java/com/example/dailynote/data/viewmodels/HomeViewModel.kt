package com.example.dailynote.data.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailynote.data.models.Note
import com.example.dailynote.data.repository.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val noteRepository: NoteRepository) : ViewModel() {


    var data by mutableStateOf<Note?>(null)

    fun setDelete(note: Note) {
        data = note
    }

    fun deleteNote() {
        viewModelScope.launch {
            noteRepository.delete(data!!)
        }
    }

    val homeUiState: StateFlow<HomeUiState> = noteRepository.getAllNotes().map { items ->
        HomeUiState(items)
    }.stateIn(
        scope = viewModelScope,
        initialValue = HomeUiState(),
        started = SharingStarted.WhileSubscribed(5000L)
    )

}

data class HomeUiState(
    val noteList: List<Note> = listOf()
)