package com.example.habittracker.ui.screens.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.TextField
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.R
import com.example.habittracker.model.HabitPriority
import com.example.habittracker.model.HabitType
import com.example.habittracker.ui.AppViewModelProvider
import com.example.habittracker.ui.HabitAppBar
import com.example.habittracker.ui.screens.navigation.NavigationDestination
import kotlin.enums.EnumEntries

object AddHabitDestination : NavigationDestination {
    override val route = "add_habit"
    override val title = R.string.create_habit
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HabitDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            HabitAppBar(
                title = stringResource(AddHabitDestination.title),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValue ->

        Column(
            modifier = modifier.padding(
                top = paddingValue.calculateTopPadding()
                    .plus(dimensionResource(R.dimen.padding_medium)),
                start = dimensionResource(R.dimen.padding_medium),
                end = dimensionResource(R.dimen.padding_medium)
            ),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {

            HabitInputForm(
                habitDetails = viewModel.entryUiState.currentHabit,
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
private fun HabitInputForm(
    habitDetails: HabitDetails,
    onValueChange: (HabitDetails) -> Unit = {},
    modifier: Modifier
) {

    TextField(
        value = habitDetails.name,
        label = { Text(text = stringResource(R.string.name)) },
        onValueChange = { onValueChange(habitDetails.copy(name = it)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    TextField(
        value = habitDetails.description,
        label = { Text(text = stringResource(R.string.description)) },
        onValueChange = { onValueChange(habitDetails.copy(description = it)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        ColorCard(
            habitColor = habitDetails.color,
            modifier = modifier.weight(0.5f)
        )
    }

    PriorityCard(
        selectedValue = habitDetails.priority,
        options = HabitPriority.entries,
        label = "Назначить приоритет",
        onOptionSelected = { onValueChange(habitDetails.copy(priority = it)) },
        modifier = modifier.fillMaxWidth()
    )

    HabitTypeCard(
        selectedValue = habitDetails.type,
        onOptionSelected = { onValueChange(habitDetails.copy(type = it)) },
        options = HabitType.entries,
        label = "Тип привычки",
    )

    TextField(
        value = habitDetails.frequency,
        label = { Text(text = stringResource(R.string.frequency)) },
        onValueChange = { onValueChange(habitDetails.copy(frequency = it)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )


    TextField(
        value = habitDetails.repeatedTimes,
        label = { Text(text = stringResource(R.string.repeatedTimes)) },
        onValueChange = { habitDetails.copy(repeatedTimes = it) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
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
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
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
                .size(dimensionResource(R.dimen.color_cirle))
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
