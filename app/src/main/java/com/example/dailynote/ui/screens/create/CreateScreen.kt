package com.example.dailynote.ui.screens.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dailynote.components.AppBarComponent
import com.example.dailynote.data.di.AppViewModelProviders
import com.example.dailynote.data.models.NoteDetails
import com.example.dailynote.data.viewmodels.AddViewModel
import com.example.dailynote.utils.Utils
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit,
    addViewModel: AddViewModel = viewModel(factory = AppViewModelProviders.fectory)
) {

    var isOpenDate by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }

    val state = rememberDatePickerState(
        initialDisplayedMonthMillis = null,
        yearRange = 2024..2024,
    )

    DatePickerDialog(
        isOpenDialog = isOpenDate,
        state = state,
        onDismissRequest = { isOpenDate = false },
        dismissButton = { isOpenDate = false },
        confirmButton = {
            isOpenDate = false
            addViewModel.updateUiState(
                noteDetails = addViewModel.addUiState.data.copy(
                    time = state.selectedDateMillis.toString(),
                    timeInMillis = state.selectedDateMillis
                ),
            )
        },
    )

    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            AppBarComponent(
                title = "Create Daily Note", isBack = true, navigateToBack = navigateToBack
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextFieldsUi(
                noteDetails = addViewModel.addUiState.data,
                onChangeValue = addViewModel::updateUiState
            )
            Spacer(modifier = Modifier.height(20.dp))

            Categories(
                noteDetails = addViewModel.addUiState.data,
                onClick = {
                    addViewModel.updateUiState(
                        noteDetails = addViewModel.addUiState.data.copy(category = it)
                    )
                    isExpanded = false
                },
                onDismissRequest = { isExpanded = false },
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it },
            )

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { isOpenDate = true }) {
                        Icon(imageVector = Icons.Outlined.DateRange, contentDescription = null)
                    }
                },
                placeholder = {
                    Text(
                        text = Utils.dateTimeFormat(state.selectedDateMillis) ?: "Select Date"
                    )
                },
                value = "",
                onValueChange = { })

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                enabled = addViewModel.addUiState.isEntryValid,
                onClick = {
                    scope.launch {
                        addViewModel.saveData()
                        navigateToBack()
                    }
                }) {
                Text(text = "Save Note")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories(
    modifier: Modifier = Modifier,
    noteDetails: NoteDetails,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onClick: (String) -> Unit,
    onDismissRequest: () -> Unit
) {

    val categoryList = listOf("Food", "Sports", "Shopping", "Tour", "Donate")
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            placeholder = {
                Text(
                    text = "Select Category"
                )
            },
            value = noteDetails.category,
            onValueChange = { })

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = Modifier.exposedDropdownSize(true)
        ) {
            categoryList.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = { onClick(it) },
                    )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    modifier: Modifier = Modifier,
    isOpenDialog: Boolean,
    state: DatePickerState,
    onDismissRequest: () -> Unit,
    dismissButton: () -> Unit,
    confirmButton: () -> Unit,
) {

    if (isOpenDialog) {
        val confirmEnable = remember { derivedStateOf { state.selectedDateMillis != null } }
        androidx.compose.material3.DatePickerDialog(
            shape = MaterialTheme.shapes.medium,
            onDismissRequest = onDismissRequest,
            dismissButton = {
                TextButton(
                    onClick = dismissButton
                ) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                TextButton(
                    enabled = confirmEnable.value,
                    onClick = confirmButton
                ) {
                    Text(text = "OK")
                }
            }) {
            DatePicker(state = state)
        }
    }

}


@Composable
fun TextFieldsUi(
    modifier: Modifier = Modifier,
    noteDetails: NoteDetails,
    onChangeValue: (NoteDetails) -> Unit
) {
    Spacer(modifier = Modifier.height(20.dp))
    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        placeholder = { Text(text = "Name") },
        value = noteDetails.name,
        onValueChange = { onChangeValue(noteDetails.copy(name = it)) })

    Spacer(modifier = Modifier.height(20.dp))

    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Decimal
        ),
        placeholder = { Text(text = "Amount") },
        value = noteDetails.amount,
        onValueChange = { onChangeValue(noteDetails.copy(amount = it)) })
}