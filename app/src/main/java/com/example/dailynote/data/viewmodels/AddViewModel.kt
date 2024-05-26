package com.example.dailynote.data.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailynote.data.models.Note
import com.example.dailynote.data.models.NoteDetails
import com.example.dailynote.data.repository.NoteRepository
import kotlinx.coroutines.launch

class AddViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    var addUiState by mutableStateOf(AddUiState())
        private set

    fun updateUiState(noteDetails: NoteDetails) {
        addUiState = AddUiState(data = noteDetails, isEntryValid = validateInput(noteDetails))
    }

    private fun validateInput(uiState: NoteDetails = addUiState.data): Boolean {
        return with(uiState) {
            addUiState.data.name.isNotBlank() && addUiState.data.amount.isNotBlank() && addUiState.data.category.isNotBlank()
        }
    }



    suspend fun saveData() {
        if (validateInput()) {
            noteRepository.insert(addUiState.data.toNote())
        }
    }

}

fun NoteDetails.toNote(): Note = Note(
    id = id,
    name = name,
    category = category,
    amount = amount.toDoubleOrNull() ?: 0.0,
    time = timeInMillis ?: 0L
)

data class AddUiState(
    val data: NoteDetails = NoteDetails(),
    val isEntryValid: Boolean = false
)