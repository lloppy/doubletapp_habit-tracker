package com.example.habittracker.ui.screens.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.R
import com.example.habittracker.model.HabitPriority
import com.example.habittracker.model.HabitType
import com.example.habittracker.ui.AppViewModelProvider
import com.example.habittracker.ui.screens.HabitAppBar
import com.example.habittracker.ui.screens.navigation.NavigationDestination
import kotlin.enums.EnumEntries

object HabitEntryDestination : NavigationDestination {
    override val route = "entry_habit"
    override val title = R.string.create_habit
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitEntryScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HabitEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val paddingMedium = dimensionResource(R.dimen.padding_medium)

    Scaffold(
        topBar = {
            HabitAppBar(
                title = stringResource(HabitEntryDestination.title),
                canNavigateBack = true,
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                navigateUp = navigateBack
            )
        }
    ) { paddingValue ->

        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    top = paddingValue.calculateTopPadding().plus(paddingMedium),
                    start = paddingMedium,
                    end = paddingMedium,
                    bottom = paddingValue.calculateBottomPadding()
                ),
            verticalArrangement = Arrangement.spacedBy(paddingMedium)
        ) {

            HabitInputForm(
                habitEntity = viewModel.entryUiState.currentHabit,
                onValueChange = viewModel::updateUiState,
                modifier = modifier
            )

            Button(
                onClick = {
                    viewModel.saveItem()
                    navigateBack()
                },
                enabled = viewModel.entryUiState.isEntryValid,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.create))
            }
        }
    }
}

@Composable
fun HabitInputForm(
    habitEntity: HabitEntity,
    onValueChange: (HabitEntity) -> Unit = {},
    modifier: Modifier
) {
    val required = "*"

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

    HabitTypeCard(
        selectedValue = habitEntity.type,
        onOptionSelected = { onValueChange(habitEntity.copy(type = it)) },
        options = HabitType.entries,
        label = stringResource(R.string.set_type, required),
    )

    ColorCard(
        habitColor = habitEntity.color,
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


@Composable
fun HabitTypeCard(
    selectedValue: String,
    onOptionSelected: (String) -> Unit = {},
    options: EnumEntries<HabitType>,
    label: String,
    modifier: Modifier = Modifier
) {
    Text(text = label)

    Column(modifier.selectableGroup()) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onOptionSelected(option.typeName) }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (option.typeName == selectedValue),
                    onClick = { onOptionSelected(option.typeName) }
                )
                Text(text = option.typeName)
            }
        }
    }
}


@Composable
fun ColorCard(
    habitColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(dimensionResource(R.dimen.color_circle))
                .clip(CircleShape)
                .background(habitColor)
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_large)))
        Text(stringResource(R.string.color))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityCard(
    selectedValue: String,
    onOptionSelected: (String) -> Unit = {},
    options: EnumEntries<HabitPriority>,
    label: String,
    modifier: Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option.priorityName) },
                    onClick = {
                        expanded = false
                        onOptionSelected(option.priorityName)
                    }
                )
            }
        }
    }
}
