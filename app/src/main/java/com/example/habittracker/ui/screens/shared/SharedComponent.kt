package com.example.habittracker.ui.screens.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.habittracker.R
import com.example.habittracker.ui.screens.HabitAppBar
import com.example.habittracker.ui.screens.item.HabitEntryDestination
import com.example.habittracker.ui.screens.item.HabitEntryViewModel
import com.example.habittracker.ui.screens.item.EditHabitDestination
import com.example.habittracker.ui.screens.item.EditHabitViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitFormScreen(
    title: String,
    navigateBack: () -> Unit,
    onSave: suspend () -> Unit,
    isEntryValid: Boolean,
    habitEntity: HabitEntity,
    onValueChange: (HabitEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            HabitAppBar(
                title = title,
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
                    top = paddingValue.calculateTopPadding()
                        .plus(dimensionResource(R.dimen.padding_medium)),
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                    bottom = paddingValue.calculateBottomPadding().plus(dimensionResource(R.dimen.padding_medium))
                ),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            HabitInputForm(
                habitEntity = habitEntity,
                onValueChange = onValueChange,
                modifier = modifier
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        onSave()
                        navigateBack()
                    }
                },
                enabled = isEntryValid,
                modifier = modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.button_size))
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}
