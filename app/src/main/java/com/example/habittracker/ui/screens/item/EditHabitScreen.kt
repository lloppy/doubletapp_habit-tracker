package com.example.habittracker.ui.screens.item

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.R
import com.example.habittracker.ui.AppViewModelProvider
import com.example.habittracker.ui.screens.HabitAppBar
import com.example.habittracker.ui.screens.shared.HabitInputForm
import com.example.habittracker.ui.screens.navigation.NavigationDestination

object EditHabitDestination : NavigationDestination {
    override val route = "edit_habit"
    override val title = R.string.item_edit_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditHabitScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditHabitViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val paddingMedium = dimensionResource(R.dimen.padding_medium)

    Scaffold(
        topBar = {
            HabitAppBar(
                title = stringResource(EditHabitDestination.title),
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
                    viewModel.updateItem()
                    navigateBack()
                },
                enabled = viewModel.entryUiState.isEntryValid,
                modifier = modifier.fillMaxWidth().height(dimensionResource(R.dimen.button_size))
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}