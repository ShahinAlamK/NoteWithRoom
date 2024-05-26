package com.example.dailynote.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dailynote.components.AppBarComponent
import com.example.dailynote.data.di.AppViewModelProviders
import com.example.dailynote.data.models.Note
import com.example.dailynote.data.viewmodels.HomeViewModel
import com.example.dailynote.utils.Utils

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToAdd: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProviders.fectory)
) {

    val uiState by homeViewModel.homeUiState.collectAsState()
    var isOpen by remember { mutableStateOf(false) }

    DeleteDialog(
        isOpen = isOpen,
        onDismissRequest = { isOpen = false },
        confirmButton = {
            homeViewModel.deleteNote()
            isOpen = false
        },
        dismissButton = { isOpen = false }
    )

    Scaffold(topBar = {
        AppBarComponent(
            title = "Daily Note", isBack = false
        )
    },

        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAdd) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }) { paddingValues ->

        if (uiState.noteList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Empty Daily Note")
            }
        }
        LazyColumn(
            modifier = modifier.padding(paddingValues)
        ) {
            items(uiState.noteList.size) {
                val note = uiState.noteList[it]
                NoteViewItem(
                    note = note,
                    index = it + 1,
                    onClick = { notes ->
                        homeViewModel.setDelete(notes)
                        isOpen = true
                    }
                )
            }
        }
    }
}

@Composable
fun NoteViewItem(
    modifier: Modifier = Modifier,
    note: Note,
    index: Int,
    onClick: (Note) -> Unit,
) {
    Surface(
        onClick = { onClick(note) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 5.dp),
        tonalElevation = 5.dp,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
        ) {
            Text(text = index.toString())
            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = note.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = Utils.dateTimeFormat(note.time) ?: "no date",
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Text(text = "TK ${note.amount}")
        }
    }
}

@Composable
fun DeleteDialog(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    dismissButton: () -> Unit,
) {
    if (isOpen) AlertDialog(onDismissRequest = onDismissRequest, confirmButton = {
        TextButton(onClick = confirmButton) {
            Text(text = "Delete")
        }
    }, dismissButton = {
        TextButton(onClick = dismissButton) {
            Text(text = "Cancel")
        }
    }, text = {
        Text(text = "Are you sure to delete this note?")
    }, title = {
        Text(text = "Delete Note")
    }, icon = {
        Icon(imageVector = Icons.Default.Warning, contentDescription = "")
    })
}