package com.example.habittracker.ui.shared.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.habittracker.R
import com.example.habittracker.ui.shared.filter.components.CategorySection
import com.example.habittracker.ui.shared.filter.components.PrioritySection
import com.example.habittracker.ui.shared.filter.components.SearchSection
import com.example.habittracker.ui.shared.filter.components.SortSection
import com.example.habittracker.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModalSheet(
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (FilterState) -> Unit,
    initialFilterState: FilterState,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            modifier = modifier.background(MaterialTheme.colorScheme.background.copy(0.5f))

        ) {
            FilterContent(
                modifier = modifier.padding(Spacing.medium),
                initialFilterState = initialFilterState,
                onSubmit = onSubmit,
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
private fun FilterContent(
    modifier: Modifier,
    initialFilterState: FilterState,
    onSubmit: (FilterState) -> Unit,
    onDismiss: () -> Unit
) {
    var filterState by remember { mutableStateOf(initialFilterState) }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
    ) {
        SearchSection(
            searchQuery = filterState.searchQuery,
            onSearchQueryChanged = { filterState = filterState.copy(searchQuery = it) }
        )

        SortSection(
            sortOption = filterState.sortOption,
            onSortOptionChanged = { filterState = filterState.copy(sortOption = it) }
        )

        CategorySection(
            selectedCategory = filterState.selectedCategory,
            onCategorySelected = { filterState = filterState.copy(selectedCategory = it) }
        )

        PrioritySection(
            selectedPriority = filterState.selectedPriority,
            onPrioritySelected = { filterState = filterState.copy(selectedPriority = it) }
        )
        Spacer(modifier = Modifier.height(Spacing.medium))

        ApplyButton(
            onClick = {
                onSubmit(filterState)
                onDismiss()
            }
        )

        ClearButton(
            onClick = {
                onSubmit(FilterState())
                onDismiss()
            }
        )
    }
}


@Composable
private fun ApplyButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(Spacing.button)
    ) {
        Text(text = stringResource(R.string.apply_filters))
    }
}

@Composable
fun ClearButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(Spacing.button),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onErrorContainer)
    ) {
        Text(text = stringResource(R.string.clear_filters))
    }
}
