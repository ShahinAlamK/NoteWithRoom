package com.example.dailynote.data.models

data class NoteDetails(
    val id: Long = 0,
    val name: String = "",
    val category: String = "",
    val amount: String = "",
    val time: String = "",
    val timeInMillis: Long? = null,
)
