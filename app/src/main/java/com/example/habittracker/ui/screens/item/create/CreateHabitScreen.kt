package com.example.habittracker.ui.screens.item.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.R
import com.example.habittracker.ui.AppViewModelProvider
import com.example.habittracker.ui.navigation.NavigationDestination
import com.example.habittracker.ui.screens.HabitAppBar
import com.example.habittracker.ui.shared.form.HabitInputForm
import com.example.habittracker.ui.theme.Spacing
import kotlinx.coroutines.launch

object HabitEntryDestination : NavigationDestination {
    override val route = "create_habit"
    override val title = R.string.create_habit
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateHabitScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateHabitViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

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
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = Spacing.medium
                )
                .padding(
                    top = paddingValue.calculateTopPadding()
                        .plus(Spacing.medium),
                    bottom = paddingValue.calculateBottomPadding()
                        .plus(Spacing.medium)
                ),
            verticalArrangement = Arrangement.spacedBy(Spacing.medium)
        ) {
            HabitInputForm(
                habitEntity = viewModel.entryUiState.currentHabit,
                onValueChange = viewModel::updateUiState,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveItem()
                        navigateBack()
                    }
                },
                enabled = viewModel.entryUiState.isEntryValid,
                modifier = modifier
                    .fillMaxWidth()
                    .height(Spacing.button)
            ) {
                Text(text = stringResource(R.string.create))
            }
        }
    }
}
