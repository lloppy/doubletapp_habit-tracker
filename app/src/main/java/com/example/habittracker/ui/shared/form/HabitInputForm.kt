package com.example.habittracker.ui.shared.form

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
import com.example.habittracker.model.HabitEntity
import com.example.habittracker.ui.screens.item.UpdateAction
import com.example.habittracker.ui.shared.ColorPickerDialog
import com.example.habittracker.ui.shared.form.components.CategoryCard
import com.example.habittracker.ui.shared.form.components.ChooseColorButton
import com.example.habittracker.ui.shared.form.components.PriorityCard
import com.example.habittracker.ui.shared.form.components.TypeCard
import com.example.model.HabitCategory
import com.example.model.HabitPriority
import com.example.model.HabitType

@Composable
fun HabitInputForm(
    habitEntity: HabitEntity,
    onAction: (UpdateAction) -> Unit,
    modifier: Modifier,
) {
    val openDialog = remember { mutableStateOf(false) }
    val selectedColor = remember { mutableStateOf(Color.Unspecified) }

    if (openDialog.value) {
        ColorPickerDialog(
            onDismissRequest = { openDialog.value = false },
            onColorSelected = {
                selectedColor.value = it
                onAction(UpdateAction.Color(it))
                openDialog.value = false
            },
            habitEntity = habitEntity
        )
    }

    OutlinedTextField(
        value = habitEntity.name,
        label = {
            Text(text = stringResource(R.string.set_name) + stringResource(R.string.required))
        },
        onValueChange = {
            onAction(UpdateAction.Name(it))
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Sentences
        ),
        singleLine = true,
        modifier = modifier
    )

    OutlinedTextField(
        value = habitEntity.frequency,
        label = {
            Text(text = stringResource(R.string.set_frequency))
        },
        onValueChange = {
            onAction(UpdateAction.Frequency(it))
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Sentences
        ),
        singleLine = true,
        modifier = modifier
    )

    OutlinedTextField(
        value = habitEntity.description,
        label = {
            Text(text = stringResource(R.string.set_description) + stringResource(R.string.required))
        },
        onValueChange = {
            onAction(UpdateAction.Description(it))
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Sentences
        ),
        singleLine = true,
        modifier = modifier
    )

    CategoryCard(
        selectedValue = habitEntity.category,
        onOptionSelected = {
            onAction(UpdateAction.Category(it))
        },
        options = HabitCategory.entries,
        label = stringResource(
            R.string.set_category,
            stringResource(R.string.required)
        ),
        modifier = modifier
    )

    ChooseColorButton(
        habitEntity = habitEntity,
        onClick = { openDialog.value = true },
        modifier = modifier
    )

    PriorityCard(
        selectedValue = habitEntity.priority,
        options = HabitPriority.entries,
        label = stringResource(R.string.set_priority),
        onOptionSelected = {
            onAction(UpdateAction.Priority(it))
        },
        modifier = modifier
    )

    TypeCard(
        selectedValue = habitEntity.type,
        onOptionSelected = {
            onAction(UpdateAction.Type(it))
        },
        options = HabitType.entries,
        label = stringResource(
            R.string.set_type,
            stringResource(R.string.required)
        ),
        modifier = modifier
    )

    OutlinedTextField(
        value = habitEntity.repeatedTimes,
        placeholder = {
            Text(text = stringResource(R.string.set_repeated_times) + stringResource(R.string.required))
        },
        onValueChange = {
            onAction(UpdateAction.RepeatedTimes(it))
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number,
            capitalization = KeyboardCapitalization.Sentences
        ),
        singleLine = true,
        modifier = modifier
    )
}
