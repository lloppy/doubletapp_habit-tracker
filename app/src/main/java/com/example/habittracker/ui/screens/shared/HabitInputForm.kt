package com.example.habittracker.ui.screens.shared

import ChooseColorButton
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.example.habittracker.R
import com.example.habittracker.model.HabitPriority
import com.example.habittracker.model.HabitType
import com.example.habittracker.ui.screens.item.HabitEntity
import com.example.habittracker.ui.screens.shared.elements.PriorityCard
import com.example.habittracker.ui.screens.shared.elements.TypeCard


@Composable
fun HabitInputForm(
    habitEntity: HabitEntity,
    onValueChange: (HabitEntity) -> Unit = {},
    modifier: Modifier
) {
    val openDialog = remember { mutableStateOf(false) }
    val selectedColor = remember { mutableStateOf(Color.Unspecified) }

    val required = "*"

    if (openDialog.value) {
        ColorPickerDialog(
            onDismissRequest = { openDialog.value = false },
            onColorSelected = {
                selectedColor.value = it
                onValueChange(habitEntity.copy(color = it))
                openDialog.value = false
            },
            habitEntity = habitEntity
        )
    }

    OutlinedTextField(
        value = habitEntity.name,
        label = { Text(text = stringResource(R.string.set_name, required)) },
        onValueChange = { onValueChange(habitEntity.copy(name = it)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Sentences
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = habitEntity.description,
        label = { Text(text = stringResource(R.string.set_description)) },
        onValueChange = { onValueChange(habitEntity.copy(description = it)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Sentences
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    TypeCard(
        selectedValue = habitEntity.type,
        onOptionSelected = { onValueChange(habitEntity.copy(type = it)) },
        options = HabitType.entries,
        label = stringResource(R.string.set_type, required),
    )

    ChooseColorButton(
        habitEntity = habitEntity,
        onClick = { openDialog.value = true },
        modifier = modifier.fillMaxWidth()
    )

    PriorityCard(
        selectedValue = habitEntity.priority,
        options = HabitPriority.entries,
        label = stringResource(R.string.set_priority),
        onOptionSelected = { onValueChange(habitEntity.copy(priority = it)) },
        modifier = modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = habitEntity.frequency,
        label = { Text(text = stringResource(R.string.set_frequency)) },
        onValueChange = { onValueChange(habitEntity.copy(frequency = it)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Sentences
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = habitEntity.repeatedTimes,
        placeholder = { Text(text = stringResource(R.string.set_repeated_times)) },
        onValueChange = { onValueChange(habitEntity.copy(repeatedTimes = it)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number,
            capitalization = KeyboardCapitalization.Sentences
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

}